package isel.pdm.chelaspokerdice.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.about.content.AboutThisProject
import isel.pdm.chelaspokerdice.screens.about.content.GamePlayOverview
import isel.pdm.chelaspokerdice.screens.about.struct.AboutScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColunmDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleTabs

class AboutScreen(
    private val onNavigateToTitleScreen: () -> Unit = {},
    private val onNavigateToGameRules: (String) -> Unit = {},
    private val onNavigateToMail: (List<String>, String) -> Unit = { _ , _ -> },
    private val selectedTabIndex: Int,
    private val onTabSelected: (Int) -> Unit
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        AboutScreenContent(Arrangement.Top){ }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        AboutScreenContent(Arrangement.Center){ }
    }

    @Composable
    private fun AboutScreenContent(arrangement: Arrangement.Vertical,content: @Composable () -> Unit) {

        val tab1 = stringResource(R.string.tab_gameplayOverview)
        val tab2 = stringResource(R.string.tab_aboutProject)

        val tabItems = mapOf(
            tab1 to @Composable { GamePlayOverview(onNavigateToGameRules) },
            tab2 to @Composable { AboutThisProject(onNavigateToMail) }
        )

        AboutScaffold(onNavigateToTitleScreen) { innerPadding ->
            ContentColunmDisplay(innerPadding,arrangement) {
                SimpleTabs(
                    tabItems = tabItems,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = {index ->  onTabSelected(index) }
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun Preview() {
    //  AboutScreen().Render(Modifier)
}


