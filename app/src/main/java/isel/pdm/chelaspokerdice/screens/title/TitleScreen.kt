package isel.pdm.chelaspokerdice.screens.title

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.title.struct.TitleScreenDrawer
import isel.pdm.chelaspokerdice.screens.title.struct.TitleScreenScaffold
import isel.pdm.chelaspokerdice.ui.components.elements.ButtonText
import isel.pdm.chelaspokerdice.ui.components.figures.PokerDiceLogo
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentRowDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.RowDivider

class TitleScreen(
    private val onNavigateToAbout: () -> Unit = {},
    private val onNavigateToPlayerProfile: () -> Unit = {},
    private val onNavigateToLobbies: () -> Unit = {},
) : Screen {
    private val buttonName
        @Composable
        get() = stringResource(R.string.lobbies)

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        TitleScreenContent { innerPadding ->
            ScrollableContentColumnDisplay(modifier,innerPadding, Arrangement.Center) {
                PokerDiceLogo()
                Spacer(modifier.padding(100.dp))
                ButtonText(buttonName) { onNavigateToLobbies() }
            }
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        TitleScreenContent { innerPadding ->
            ContentRowDisplay(innerPadding) {
                RowDivider(0.5f) { PokerDiceLogo(size = 225) }
                RowDivider(1f, Alignment.CenterEnd, 75) {
                    ButtonText(buttonName) { onNavigateToLobbies() }
                }
            }
        }
    }

    @Composable
    private fun TitleScreenContent(content: @Composable (PaddingValues) -> Unit) {
        TitleScreenDrawer(onNavigateToPlayerProfile, onNavigateToAbout) { onOpenDrawer ->
            TitleScreenScaffold(Modifier,onOpenDrawer) { innerPadding ->
                content(innerPadding)
            }
        }
    }
}

@PreviewScreenSizes()
@Composable
private fun Preview() {
    TitleScreen().Render(Modifier)
}

