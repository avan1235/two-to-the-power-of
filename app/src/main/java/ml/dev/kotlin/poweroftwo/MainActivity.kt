package ml.dev.kotlin.poweroftwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.SaveAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.dev.kotlin.poweroftwo.game.GameBoard
import ml.dev.kotlin.poweroftwo.ui.Board
import ml.dev.kotlin.poweroftwo.ui.Stats
import ml.dev.kotlin.poweroftwo.ui.theme.FontDark
import ml.dev.kotlin.poweroftwo.ui.theme.FontLight
import ml.dev.kotlin.poweroftwo.ui.theme.Surface
import ml.dev.kotlin.poweroftwo.ui.theme.TwoToThePowerOfTenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoToThePowerOfTenTheme {
                Surface(color = Surface) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Game()
                        }
                    }
                }
            }
        }
    }
}

private val BOARD_SIZES = listOf(3, 4, 5, 6, 7)

@Composable
private fun Game() {
    var gameSize by remember { mutableStateOf(4) }
    val gameBoard = remember(gameSize) { mutableStateOf(GameBoard.random(gameSize)) }
    val points = remember(gameSize) { mutableStateOf(0) }
    val drag = remember(gameSize) { mutableStateOf(Offset.Zero) }
    val onRestart = {
        gameBoard.value = GameBoard.random(gameSize)
        points.value = 0
        drag.value = Offset.Zero
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(gameSize, gameSwipeGesture(gameBoard, drag, points)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ControlButtons(
            onRestart = onRestart,
            onSave = {}
        )
        Spacer(modifier = Modifier.weight(1f))
        Board(gameBoard.value)
        Spacer(modifier = Modifier.weight(1f))
        SizeButtons { gameSize = it }
    }
    if (gameBoard.value.isFinished()) {
        Stats(score = points.value, onRestart)
    }
}

@Composable
private fun SizeButtons(onSizeChange: (size: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BOARD_SIZES.forEach { size ->
            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .size(48.dp),
                onClick = { onSizeChange(size) },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = FontDark,
                    contentColor = FontLight,
                )
            ) {
                Text(
                    text = "$size",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ControlButtons(onRestart: () -> Unit, onSave: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onSave) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.Save,
                contentDescription = "save",
                tint = FontDark
            )
        }
        IconButton(onClick = onRestart) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.Default.RestartAlt,
                contentDescription = "restart",
                tint = FontDark
            )
        }
    }
}