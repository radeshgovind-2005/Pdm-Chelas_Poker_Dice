package isel.pdm.chelaspokerdice.activities.playerprofile

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.activities.playerprofile.compose.PortraitPlayerProfileCompose
import isel.pdm.chelaspokerdice.components.button.LandscapeButton
import isel.pdm.chelaspokerdice.components.button.PortraitButton
import isel.pdm.chelaspokerdice.components.screen.Screen

class PlayerProfileScreen(
    private val navMap: Map<(() -> Unit), Int>,
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        PortraitPlayerProfileCompose {
            navMap.forEach {
                PortraitButton(it.key, stringResource(it.value))
            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        PlayerProfileStructure {
            navMap.forEach {
                LandscapeButton(it.key, stringResource(it.value))
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    val map = mapOf(
        { } to R.string.title_screen,
    )
    PlayerProfileScreen(map).Render(Modifier)
}
