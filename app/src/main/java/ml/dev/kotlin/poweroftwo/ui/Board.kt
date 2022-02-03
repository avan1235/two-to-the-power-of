package ml.dev.kotlin.poweroftwo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ml.dev.kotlin.poweroftwo.game.GameBoard
import ml.dev.kotlin.poweroftwo.ui.theme.Background
import ml.dev.kotlin.poweroftwo.ui.theme.Shapes
import kotlin.math.min

@Composable
fun Board(gameBoard: GameBoard, blockSize: Dp) {
    Box(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(Background)
            .padding(all = 4.dp)
            .size(blockSize * gameBoard.gameSize)
    ) {
        for (y in gameBoard.gameRange) for (x in gameBoard.gameRange)
            gameBoard[x, y]?.let { Cell(it, blockSize, x, y) }
    }
}