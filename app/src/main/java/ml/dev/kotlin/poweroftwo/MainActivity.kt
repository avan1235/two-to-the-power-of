package ml.dev.kotlin.poweroftwo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.dev.kotlin.poweroftwo.game.GameBoard
import ml.dev.kotlin.poweroftwo.ui.Board
import ml.dev.kotlin.poweroftwo.ui.Stats
import ml.dev.kotlin.poweroftwo.ui.theme.FontDark
import ml.dev.kotlin.poweroftwo.ui.theme.FontLight
import ml.dev.kotlin.poweroftwo.ui.theme.Surface
import ml.dev.kotlin.poweroftwo.ui.theme.PowerOfTwoTheme
import ml.dev.kotlin.poweroftwo.util.OrientationBased

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerOfTwoTheme {
                Surface(color = Surface) { Game() }
            }
        }
    }
}

private val BOARD_SIZES = listOf(2, 3, 4, 5, 6, 7)

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

    OrientationBased(
        portrait = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(gameSize, gameSwipeGesture(gameBoard, drag, points)),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBar(onRestart = onRestart, points = points.value)
                Board(gameBoard.value)
                BottomBar(onSizeChange = { gameSize = it })
            }
        },
        landscape = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(gameSize, gameSwipeGesture(gameBoard, drag, points)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TopBar(onRestart = onRestart, points = points.value)
                Board(gameBoard.value)
                BottomBar(onSizeChange = { gameSize = it })
            }
        }
    )
    if (gameBoard.value.isFinished()) {
        Stats(score = points.value, onRestart)
    }
}

@Composable
private fun BottomBar(onSizeChange: (size: Int) -> Unit) {

    @Composable
    fun BottomBarElements() {
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
                )
            }
        }
    }

    OrientationBased(
        portrait = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                BottomBarElements()
            }
        },
        landscape = {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BottomBarElements()
            }
        }
    )
}

@Composable
private fun TopBar(onRestart: () -> Unit, points: Int) {

    @Composable
    fun TopBarElements() {
        Box(modifier = Modifier.size(36.dp))
        Text(
            text = "$points",
            fontSize = 36.sp,
        )
        IconButton(onClick = onRestart) {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Loop,
                contentDescription = "restart",
                tint = FontDark
            )
        }
    }

    OrientationBased(
        portrait = {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TopBarElements()
            }
        },
        landscape = {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TopBarElements()
            }
        }
    )
}