package ml.dev.kotlin.poweroftwo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ml.dev.kotlin.poweroftwo.game.BoardCell
import ml.dev.kotlin.poweroftwo.game.EmptyCell
import ml.dev.kotlin.poweroftwo.game.NumberCell
import ml.dev.kotlin.poweroftwo.ui.theme.BackgroundEmpty
import ml.dev.kotlin.poweroftwo.ui.theme.Shapes
import ml.dev.kotlin.poweroftwo.ui.theme.backgroundColor
import ml.dev.kotlin.poweroftwo.ui.theme.fontColor

@Composable
fun Cell(boardCell: BoardCell, blockSize: Dp) {
    Box(
        modifier = Modifier
            .size(blockSize)
            .padding(all = 4.dp)
            .clip(Shapes.medium)
            .background(
                when (boardCell) {
                    is NumberCell -> boardCell.number.backgroundColor
                    EmptyCell -> BackgroundEmpty
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        if (boardCell is NumberCell) {
            Text(
                text = "${boardCell.number}",
                color = boardCell.number.fontColor,
                fontSize = with(LocalDensity.current) { (blockSize / 3).toSp() },
            )
        }
    }
}
