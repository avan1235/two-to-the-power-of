package ml.dev.kotlin.poweroftwo.game

import kotlin.random.Random

data class GameBoard(
    private val cells: List<BoardCell>,
    val gameSize: Int,
) {
    operator fun get(x: Int, y: Int): BoardCell? = cells[x, y]

    val gameRange: IntRange = 0 until gameSize

    fun swipe(swipe: Swipe): SwipeResult {
        val mutatedValues = cells.toMutableList()
        val after = swipe.after
        var points = 0

        for (rowCol in gameSize - 1 downTo 0) for (moving in swipe.movingRange(0, gameSize - 1)) {
            val curr = swipe.create(moving, rowCol)
            val currValue = mutatedValues[curr] ?: continue
            val currNumber = if (currValue is NumberCell) currValue.number else continue
            val next = generateSequence(curr.let(after), after).take(gameSize).reduce { acc, xy ->
                when (mutatedValues[xy]) {
                    is NumberCell -> if (mutatedValues[acc] is NumberCell) acc else xy
                    EmptyCell -> if (mutatedValues[acc] == EmptyCell) xy else acc
                    null -> acc
                }
            }
            when (val nextValue = mutatedValues[next] ?: continue) {
                EmptyCell -> {
                    mutatedValues[curr] = EmptyCell
                    mutatedValues[next] = currValue
                }
                is NumberCell -> if (nextValue.number == currNumber) {
                    mutatedValues[curr] = EmptyCell
                    mutatedValues[next] = currValue.copy(number = currNumber shl 1)
                    points += currNumber
                } else {
                    mutatedValues[curr] = EmptyCell
                    mutatedValues[swipe.before(next)] = currValue
                }
            }
        }
        val updated = if (mutatedValues != cells) addRandom(mutatedValues) else cells
        return SwipeResult(gameBoard = copy(cells = updated), points)
    }

    fun isFinished(): Boolean = Swipe.values().none { swipe(it).gameBoard != this }

    private operator fun <V> MutableList<V>.set(x: Int, y: Int, value: V): Unit =
        if (x in gameRange && y in gameRange) this[y * gameSize + x] = value else Unit

    private operator fun <V> List<V>.get(x: Int, y: Int): V? =
        if (x in gameRange && y in gameRange) getOrNull(y * gameSize + x) else null

    private operator fun <V> List<V>.get(xy: XY): V? = get(xy.x, xy.y)
    private operator fun <V> MutableList<V>.set(xy: XY, value: V): Unit = set(xy.x, xy.y, value)

    companion object {
        fun random(gameSize: Int): GameBoard = GameBoard(
            cells = generateSequence(randomCell).take(gameSize * gameSize).toList(),
            gameSize = gameSize
        )
    }
}

data class SwipeResult(val gameBoard: GameBoard, val points: Int)

private val randomCell: () -> BoardCell = {
    if (Random.nextBoolean()) randomNumberCell() else EmptyCell
}

private val randomNumberCell: () -> NumberCell = {
    NumberCell(Random.nextInt(from = 0, until = 8192).takeLowestOneBit() shl 1)
}

private fun addRandom(cells: MutableList<BoardCell>): List<BoardCell> = cells.apply {
    withIndex().filter { it.value == EmptyCell }
        .randomOrNull()?.index
        ?.let { this[it] = randomNumberCell() }
}
