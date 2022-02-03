package ml.dev.kotlin.poweroftwo.game

data class GameBoard(
    private val cells: List<BoardCell>,
    val gameSize: Int,
) {
    init {
        assert(gameSize * gameSize == cells.size)
    }

    operator fun get(x: Int, y: Int): BoardCell? = cells[x, y]

    val gameRange: IntRange = 0 until gameSize

    fun swipe(swipe: Swipe): GameBoard {
        val mutatedValues = cells.toMutableList()
        val after = swipe.after

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
                } else {
                    mutatedValues[curr] = EmptyCell
                    mutatedValues[swipe.before(next)] = currValue
                }
            }
        }

        return copy(cells = mutatedValues)
    }

    private operator fun <V> MutableList<V>.set(x: Int, y: Int, value: V): Unit =
        if (x in gameRange && y in gameRange) this[y * gameSize + x] = value else Unit

    private operator fun <V> List<V>.get(x: Int, y: Int): V? =
        if (x in gameRange && y in gameRange) getOrNull(y * gameSize + x) else null

    private operator fun <V> List<V>.get(xy: XY): V? = get(xy.x, xy.y)
    private operator fun <V> MutableList<V>.set(xy: XY, value: V): Unit = set(xy.x, xy.y, value)
}

fun randomGame(gameSize: Int): GameBoard = GameBoard(
    cells = listOf(
        2,
        2,
        2,
        2,
        32,
        64,
        128,
        256,
        512,
        1024,
        2048,
        4096,
        8192,
        null, null, null,
    ).map(TO_BOARD_CELL),
    gameSize
)

private val TO_BOARD_CELL: (Int?) -> BoardCell = { v -> v?.let { NumberCell(it) } ?: EmptyCell }
