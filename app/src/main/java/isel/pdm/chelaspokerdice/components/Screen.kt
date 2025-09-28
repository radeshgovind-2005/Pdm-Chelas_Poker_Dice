package isel.pdm.chelaspokerdice.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

interface Screen {
    @Composable
    fun PortraitScreen(modifier: Modifier)

    @Composable
    fun LandscapeScreen(modifier: Modifier)

    @Composable
    fun Render(modifier: Modifier) {
        val orientation = LocalConfiguration.current.orientation

        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> LandscapeScreen(modifier)
            Configuration.ORIENTATION_PORTRAIT -> PortraitScreen(modifier)
            else -> throw IllegalArgumentException("Invalid screen orientation: $orientation")
        }
    }
}