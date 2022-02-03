package ml.dev.kotlin.poweroftwo

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.consumeAllChanges
import ml.dev.kotlin.poweroftwo.game.GameBoard
import ml.dev.kotlin.poweroftwo.game.Swipe
import kotlin.math.abs

fun gameSwipeGesture(
    gameBoardState: MutableState<GameBoard>,
    dragState: MutableState<Offset>,
): suspend PointerInputScope.() -> Unit = {
    var drag by dragState
    var gameBoard by gameBoardState
    detectDragGestures(
        onDragEnd = {
            val (x, y) = drag
            val isHorizontal = abs(x) >= abs(y)
            val swipe = when {
                x > 0 && isHorizontal -> Swipe.RIGHT
                x < 0 && isHorizontal -> Swipe.LEFT
                y > 0 && !isHorizontal -> Swipe.DOWN
                y < 0 && !isHorizontal -> Swipe.UP
                else -> null
            }
            swipe?.let { gameBoard = gameBoard.swipe(it) }
            drag = Offset.Zero
        },
        onDrag = { change, dragAmount ->
            change.consumeAllChanges()
            drag += dragAmount
        })
}
