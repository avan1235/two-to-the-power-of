package ml.dev.kotlin.poweroftwo.game

sealed interface BoardCell
object EmptyCell : BoardCell
data class NumberCell(val number: Int) : BoardCell

data class XY(val x: Int, val y: Int)

infix fun Int.directedTo(other: Int): IntProgression =
    if (this <= other) this..other else this downTo other
