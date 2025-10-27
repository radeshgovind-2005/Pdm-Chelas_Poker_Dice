package isel.pdm.chelaspokerdice.screens.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.lobbies.RotatingProgressIndicator
import isel.pdm.chelaspokerdice.screens.lobby.struct.LobbyHeaderCard
import isel.pdm.chelaspokerdice.screens.lobby.struct.LobbyScaffold
import isel.pdm.chelaspokerdice.screens.lobby.struct.PlayersSection
import isel.pdm.chelaspokerdice.ui.components.elements.IconButton
import isel.pdm.chelaspokerdice.ui.components.elements.LargeText
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay
import isel.pdm.chelaspokerdice.vm.LobbyViewModel

const val LOBBY_SCAFFOLD_TAG = "lobby_scaffold"
const val LOBBY_CONTENT_TAG = "lobby_content"

class LobbyScreen(
    private val onNavigateBack: () -> Unit = {},
    private val onNavigateGame: () -> Unit = {},
    private val lobbyViewModel: LobbyViewModel
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbyScreenContent()
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbyScreenContent()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LobbyScreenContent() {
        LobbyScaffold(
            modifier = Modifier.testTag(LOBBY_SCAFFOLD_TAG),
            onClickMenu = { onNavigateBack() },
        ) { innerPadding ->
            ScrollableContentColumnDisplay(
                modifier = Modifier.testTag(LOBBY_CONTENT_TAG),
                innerPadding = innerPadding,
                vArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val state =lobbyViewModel.state
                when(state){
                    is LobbyViewModel.State.InLobby -> {
                        LobbyHeaderCard(state.lobby)
                        PlayersSection(state.lobby.lobbyPlayers)
                        Spacer(modifier = Modifier.height(8.dp))
                        IconButton(onNavigateGame,Icons.Default.PlayArrow,"Start Game","Start Game")
                    }
                    else -> {}
                }

            }
        }
    }
}