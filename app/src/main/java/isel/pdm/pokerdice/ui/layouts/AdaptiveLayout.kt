package isel.pdm.pokerdice.ui.layouts

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun AdaptiveLayout(
    landscape: @Composable () -> Unit,
    portrait: @Composable () -> Unit,
) {
    when (LocalConfiguration.current.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> landscape()
        else -> portrait()
    }
}