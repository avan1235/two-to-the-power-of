package ml.dev.kotlin.poweroftwo.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ml.dev.kotlin.poweroftwo.game.EmptyCell
import ml.dev.kotlin.poweroftwo.game.GameBoard
import ml.dev.kotlin.poweroftwo.game.NumberCell
import ml.dev.kotlin.poweroftwo.ui.theme.Background
import ml.dev.kotlin.poweroftwo.ui.theme.Shapes
import kotlin.math.min

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Board(gameBoard: GameBoard) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val blockSize = min(screenWidth, screenHeight).dp / (gameBoard.gameSize + 1)
    Box(
        modifier = Modifier
            .clip(Shapes.medium)
            .background(Background)
            .padding(all = 4.dp)
            .size(blockSize * gameBoard.gameSize)
    ) {
        Column {
            for (y in gameBoard.gameRange) Row {
                for (x in gameBoard.gameRange)
                    when (val cell = gameBoard[x, y]) {
                        is NumberCell ->
                            if (!cell.added) Cell(cell, blockSize)
                            else AnimatedCell(cell, blockSize)
                        EmptyCell -> Cell(cell, blockSize)
                        else -> Unit
                    }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun AnimatedCell(cell: NumberCell, blockSize: Dp) {
    val state = remember {
        MutableTransitionState(false)
    }.apply { targetState = true }
    AnimatedVisibility(
        visibleState = state,
        enter = scaleIn(
            transformOrigin = TransformOrigin.Center,
            animationSpec = tween(
                durationMillis = 256,
                delayMillis = 1,
                easing = FastOutSlowInEasing
            )
        ),
    ) {
        Cell(cell, blockSize)
    }
}