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
                Surface(color = Surface) {
                    Aligned(alignment = Alignment.Center) {
                        val gameBoard = remember { mutableStateOf(GameBoard.random(gameSize = 4)) }
                        val drag = remember { mutableStateOf(Offset.Zero) }
                        Aligned(
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .pointerInput(Unit, gameSwipeGesture(gameBoard, drag))
                        ) {
                            Board(gameBoard.value)
                        }
                    }
                }
            }
        }
    }
}
