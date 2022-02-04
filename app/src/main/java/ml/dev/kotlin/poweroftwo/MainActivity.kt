package ml.dev.kotlin.poweroftwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import ml.dev.kotlin.poweroftwo.game.*
import ml.dev.kotlin.poweroftwo.ui.Board
import ml.dev.kotlin.poweroftwo.ui.Stats
import ml.dev.kotlin.poweroftwo.ui.theme.*
import ml.dev.kotlin.poweroftwo.util.Aligned

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoToThePowerOfTenTheme {
                Surface(color = Surface) {
                    Aligned(alignment = Alignment.Center) {
                        val gameBoard = remember { mutableStateOf(GameBoard.random(gameSize = 4)) }
                        val points = remember { mutableStateOf(0) }
                        val drag = remember { mutableStateOf(Offset.Zero) }

                        Aligned(
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .pointerInput(Unit, gameSwipeGesture(gameBoard, drag, points))
                        ) {
                            Board(gameBoard.value)
                        }
                        if (gameBoard.value.isFinished()) {
                            Stats(score = points.value) {
                                gameBoard.value = GameBoard.random(gameSize = 2)
                                points.value = 0
                                drag.value = Offset.Zero
                            }
                        }
                    }
                }
            }
        }
    }
}
