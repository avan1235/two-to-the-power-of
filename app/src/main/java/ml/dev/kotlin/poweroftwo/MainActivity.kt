package ml.dev.kotlin.poweroftwo

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.dev.kotlin.poweroftwo.game.*
import ml.dev.kotlin.poweroftwo.ui.Board
import ml.dev.kotlin.poweroftwo.ui.theme.*
import ml.dev.kotlin.poweroftwo.util.Aligned

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoToThePowerOfTenTheme {
                Surface(color = Surface) {
                    Aligned(alignment = Alignment.Center) {
                        val gameBoard = remember { mutableStateOf(GameBoard.random(gameSize = 2)) }
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
                            EndOfGame(score = points.value) {
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

@Composable
fun EndOfGame(score: Int, reset: () -> Unit) {
    Aligned(alignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .clip(Shapes.large)
                .background(Surface)
                .border(width = 4.dp, color = Background, shape = Shapes.large)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = "Game Over",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Your Score: $score",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .clip(Shapes.medium)
                    .background(FontDark)
                    .padding(4.dp)
                    .clickable { reset() },
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = "restart",
                        tint = FontLight
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Restart",
                        color = FontLight,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}