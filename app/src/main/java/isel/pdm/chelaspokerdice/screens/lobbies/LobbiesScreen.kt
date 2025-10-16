package isel.pdm.chelaspokerdice.screens.lobbies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.lobbies.struct.LobbiesScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColumnDisplay


const val ADD_BUTTON_VIEW = "add button"
const val LOBBIES_SCAFFOLD_TAG = "lobbies_scaffold"
const val LOBBIES_CONTENT_TAG = "lobbies_content"

class LobbiesScreen(
    private val onNavigateToTitleScreen: () -> Unit = {}
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbiesScreenContent{

        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbiesScreenContent{
        }
    }

    @Composable
    private fun LobbiesScreenContent(content: @Composable () -> Unit) {
        LobbiesScaffold(modifier = Modifier.testTag(LOBBIES_SCAFFOLD_TAG), onNavigateToTitleScreen) { innerPadding ->
            ContentColumnDisplay(Modifier.testTag(LOBBIES_CONTENT_TAG), innerPadding , Arrangement.Center) {
                content()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    LobbiesScreen().Render(Modifier)
}



