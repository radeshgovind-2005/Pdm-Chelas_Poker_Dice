package isel.pdm.chelaspokerdice.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.screens.about.content.AboutThisProject
import isel.pdm.chelaspokerdice.screens.about.content.GamePlayOverview
import isel.pdm.chelaspokerdice.screens.about.struct.AboutScaffold
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.ui.components.contentDisplay.ContentColunmDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleTabs

class AboutScreen(
    private val onNavigateToTitleScreen: () -> Unit = {},
    private val onNavigateToGameRules: (String) -> Unit = {}
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        AboutScaffold(onNavigateToTitleScreen) { innerPadding ->
            ContentColunmDisplay(innerPadding, Arrangement.Top) {
                SimpleTabs(
                    mapOf(
                        "Gameplay Overview" to { GamePlayOverview(onNavigateToGameRules) },
                        "About this Project" to { AboutThisProject() }
                    )
                )
            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        AboutScaffold(onNavigateToTitleScreen) { innerPadding ->
            ContentColunmDisplay(innerPadding) {
                SimpleTabs(
                    mapOf(
                        "Gameplay Overview" to { GamePlayOverview(onNavigateToGameRules) },
                        "About this Project" to { AboutThisProject() }
                    )
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    AboutScreen().Render(Modifier)
}


