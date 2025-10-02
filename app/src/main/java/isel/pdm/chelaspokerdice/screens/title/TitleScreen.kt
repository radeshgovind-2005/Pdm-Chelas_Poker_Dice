package isel.pdm.chelaspokerdice.screens.title

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.title.struct.TitleScreenDrawer
import isel.pdm.chelaspokerdice.screens.title.struct.TitleScreenScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColunmDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentRowDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.RowDivider
import isel.pdm.chelaspokerdice.ui.components.elements.ButtonText
import isel.pdm.chelaspokerdice.ui.components.figures.PokerDiceLogo

class TitleScreen(
    private val onNavigateToAbout: () -> Unit = {},
    private val onNavigateToPlayerProfile: () -> Unit = {},
    private val onNavigateToLobbies: () -> Unit = {},
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        TitleScreenDrawer(onNavigateToPlayerProfile, onNavigateToAbout) { onOpenDrawer ->
            TitleScreenScaffold(onOpenDrawer) { innerPadding ->
                ContentColunmDisplay(innerPadding) {
                    //SCREEN CONTENT
                    PokerDiceLogo()
                    Spacer(modifier.padding(100.dp))
                    ButtonText("Lobbies") { onNavigateToLobbies() }
                }
            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        TitleScreenDrawer(onNavigateToPlayerProfile, onNavigateToAbout) { onOpenDrawer ->
            TitleScreenScaffold(onOpenDrawer) { innerPadding ->
                ContentRowDisplay(innerPadding){
                    //SCREEN CONTENT
                    RowDivider(0.5f){
                        PokerDiceLogo(size=225)
                    }
                    RowDivider(1f, Alignment.CenterEnd,75){
                        ButtonText("Lobbies") { onNavigateToLobbies() }
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes()
@Composable
private fun Preview() {
    TitleScreen().Render(Modifier)
}

