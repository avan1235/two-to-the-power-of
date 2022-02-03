package ml.dev.kotlin.poweroftwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.dev.kotlin.poweroftwo.game.*
import ml.dev.kotlin.poweroftwo.ui.Board
import ml.dev.kotlin.poweroftwo.ui.theme.*
import ml.dev.kotlin.poweroftwo.util.Aligned
import kotlin.math.abs
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoToThePowerOfTenTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Aligned(alignment = Alignment.Center) {
                        var gameBoard by remember { mutableStateOf(randomGame(gameSize = 4)) }
                        val configuration = LocalConfiguration.current
                        val screenHeight = configuration.screenHeightDp
                        val screenWidth = configuration.screenWidthDp
                        val blockSize = min(screenWidth, screenHeight).dp / (gameBoard.gameSize + 1)
                        var drag = Offset.Zero
                        Aligned(
                            modifier = Modifier.pointerInput(Unit) {
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
                            },
                            alignment = Alignment.Center
                        ) {
                            Board(gameBoard, blockSize)
                        }
                    }
                }
            }
        }
    }
}
