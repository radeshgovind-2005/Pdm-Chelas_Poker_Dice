package isel.pdm.chelaspokerdice.screens.playerprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.screens.playerprofile.struct.PlayerProfileScaffold
import isel.pdm.chelaspokerdice.components.contentDisplay.ContentColunmDisplay
import isel.pdm.chelaspokerdice.screens.Screen

class PlayerProfileScreen(
    private val onNavigateToTitleScreen: () -> Unit = {},
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        PlayerProfileScaffold(onNavigateToTitleScreen){ innerPadding ->
            ContentColunmDisplay(innerPadding) {

            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        PlayerProfileScaffold(onNavigateToTitleScreen){ innerPadding ->
            ContentColunmDisplay(innerPadding) {

            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    PlayerProfileScreen().Render(Modifier)
}
