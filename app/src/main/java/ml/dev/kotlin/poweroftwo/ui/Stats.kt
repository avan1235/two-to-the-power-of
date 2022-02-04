package ml.dev.kotlin.poweroftwo.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ml.dev.kotlin.poweroftwo.game.EmptyCell
import ml.dev.kotlin.poweroftwo.game.NumberCell
import ml.dev.kotlin.poweroftwo.ui.theme.*
import ml.dev.kotlin.poweroftwo.util.Aligned


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Stats(score: Int, reset: () -> Unit) {
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
                    .clickable(onClick = reset),
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

private fun <T> cellAnimationSpec(): FiniteAnimationSpec<T> = tween(
    durationMillis = 3000,
    delayMillis = 0,
    easing = LinearEasing
)