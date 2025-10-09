package isel.pdm.chelaspokerdice.screens.playerprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.playerprofile.struct.PlayerProfileScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColumnDisplay

const val BACK_BUTTON = "back button"

class PlayerProfileScreen(
    private val onNavigateToTitleScreen: () -> Unit = {},
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        PlayerProfileScreenContent{
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        PlayerProfileScreenContent{
        }
    }

    @Composable
    fun PlayerProfileScreenContent(content: @Composable () -> Unit) {
        PlayerProfileScaffold(onNavigateToTitleScreen){ innerPadding ->
            ContentColumnDisplay(innerPadding, Arrangement.Center) {
                content()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    PlayerProfileScreen().Render(Modifier)
}
