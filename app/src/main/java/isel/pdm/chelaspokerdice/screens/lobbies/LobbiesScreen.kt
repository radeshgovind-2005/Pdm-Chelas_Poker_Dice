package isel.pdm.chelaspokerdice.screens.lobbies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.screens.lobbies.struct.LobbiesScaffold
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColunmDisplay


class LobbiesScreen(
    private val onNavigateToTitleScreen: () -> Unit = {}
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbiesScaffold(onNavigateToTitleScreen) { innerPadding ->
            ContentColunmDisplay(innerPadding) {

            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbiesScaffold(onNavigateToTitleScreen) { innerPadding ->
            ContentColunmDisplay(innerPadding) {

            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    LobbiesScreen().Render(Modifier)
}



