package isel.pdm.pokerdice.ui.components.layout

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun AdaptiveLayoutContent(
    landscape: @Composable () -> Unit,
    portrait: @Composable () -> Unit,
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> landscape()
        else -> portrait()
    }
}