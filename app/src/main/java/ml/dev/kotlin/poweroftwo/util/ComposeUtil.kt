package ml.dev.kotlin.poweroftwo.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun OrientationBased(
    portrait: @Composable() () -> Unit,
    landscape: @Composable() () -> Unit
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> landscape()
        else -> portrait()
    }
}