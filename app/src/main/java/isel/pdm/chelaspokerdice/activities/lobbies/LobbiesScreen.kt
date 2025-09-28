package isel.pdm.chelaspokerdice.activities.lobbies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.activities.lobbies.struct.LobbiesScaffold
import isel.pdm.chelaspokerdice.components.contentDisplay.ContentColunmDisplay
import isel.pdm.chelaspokerdice.components.Screen


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



