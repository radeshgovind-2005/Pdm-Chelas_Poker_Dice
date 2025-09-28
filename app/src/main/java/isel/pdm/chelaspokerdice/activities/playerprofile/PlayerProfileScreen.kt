package isel.pdm.chelaspokerdice.activities.playerprofile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.activities.playerprofile.struct.PlayerProfileScaffold
import isel.pdm.chelaspokerdice.components.contentDisplay.ContentColunmDisplay
import isel.pdm.chelaspokerdice.components.Screen

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
