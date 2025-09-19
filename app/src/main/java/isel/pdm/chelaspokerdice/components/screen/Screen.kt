package isel.pdm.chelaspokerdice.components.screen

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import isel.pdm.chelaspokerdice.activities.title.TitleScreen

interface Screen {
    @Composable
    fun PortraitScreen(modifier: Modifier)

    @Composable
    fun LandscapeScreen(modifier: Modifier)

    @Composable
    fun Render(modifier: Modifier) {
        val config = LocalConfiguration.current
        var orientation by rememberSaveable { mutableIntStateOf(config.orientation) }

        when(orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> LandscapeScreen(modifier)
            Configuration.ORIENTATION_PORTRAIT -> PortraitScreen(modifier)
            else -> throw IllegalArgumentException("Invalid screen orientation: $orientation")
        }
    }

}
