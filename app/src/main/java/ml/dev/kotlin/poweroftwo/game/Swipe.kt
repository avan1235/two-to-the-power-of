package ml.dev.kotlin.poweroftwo.game

enum class Swipe(
    val after: (XY) -> XY,
    val before: (XY) -> XY,
    val create: (moving: Int, rowCol: Int) -> XY,
    val movingRange: (low: Int, high: Int) -> IntProgression,
) {
    RIGHT(
        after = { (x, y) -> XY(x + 1, y) },
        before = { (x, y) -> XY(x - 1, y) },
        create = { moving, rowCol -> XY(moving, rowCol) },
        movingRange = { low, high -> high directedTo low }
    ),
    LEFT(
        after = { (x, y) -> XY(x - 1, y) },
        before = { (x, y) -> XY(x + 1, y) },
        create = { moving, rowCol -> XY(moving, rowCol) },
        movingRange = { low, high -> low directedTo high }
    ),
    DOWN(
        after = { (x, y) -> XY(x, y + 1) },
        before = { (x, y) -> XY(x, y - 1) },
        create = { moving, rowCol -> XY(rowCol, moving) },
        movingRange = { low, high -> high directedTo low }
    ),
    UP(
        after = { (x, y) -> XY(x, y - 1) },
        before = { (x, y) -> XY(x, y + 1) },
        create = { moving, rowCol -> XY(rowCol, moving) },
        movingRange = { low, high -> low directedTo high }
    ),
}
