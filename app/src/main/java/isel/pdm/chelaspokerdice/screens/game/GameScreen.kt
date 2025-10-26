package isel.pdm.chelaspokerdice.screens.game

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.game.struct.GameScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay

class GameScreen(
    private val onNavigateToHome: () -> Unit = {}
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbyScreen{
        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbyScreen{}
    }

    @SuppressLint("NotConstructor")
    @Composable
    private fun LobbyScreen(content: @Composable () -> Unit) {
        GameScaffold(
            modifier = Modifier,
            onNavigateToHome,
        ) { innerPadding ->
            ScrollableContentColumnDisplay(Modifier, innerPadding , Arrangement.Top) {
                content()
            }
        }
    }
}

