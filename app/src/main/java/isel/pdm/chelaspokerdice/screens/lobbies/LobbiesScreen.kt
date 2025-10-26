package isel.pdm.chelaspokerdice.screens.lobbies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.lobbies.struct.LobbiesScaffold
import isel.pdm.chelaspokerdice.services.model.Lobby
import isel.pdm.chelaspokerdice.ui.components.elements.SimpleText
import isel.pdm.chelaspokerdice.ui.components.elements.TitleText
import isel.pdm.chelaspokerdice.ui.components.struct.SimpleSearchBar
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColumnDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard
import isel.pdm.chelaspokerdice.vm.LobbyViewModel


const val ADD_BUTTON_VIEW = "add button"
const val LOBBIES_SCAFFOLD_TAG = "lobbies_scaffold"
const val LOBBIES_CONTENT_TAG = "lobbies_content"

class LobbiesScreen(
    private val onNavigateToTitleScreen: () -> Unit = {},
    private val onNavigateToCreateLobby: () -> Unit = {},
    private val onNavigateToLobby: () -> Unit = {},
    private val lobbyViewModel: LobbyViewModel
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbiesScreenContent {

        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbiesScreenContent {
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LobbiesScreenContent(content: @Composable () -> Unit) {
        val state = lobbyViewModel.state
        LobbiesScaffold(
            modifier = Modifier.testTag(LOBBIES_SCAFFOLD_TAG),
            onNavigateToTitleScreen,
            onNavigateToCreateLobby
        ) { innerPadding ->
            ContentColumnDisplay(
                Modifier.testTag(LOBBIES_CONTENT_TAG),
                innerPadding,
                Arrangement.Top
            ) {
                SimpleSearchBar(lobbyViewModel)
                Spacer(Modifier.height(16.dp))
                when (state) {
                    is LobbyViewModel.State.Idle -> Text("No lobbies loaded")
                    is LobbyViewModel.State.Loading -> CircularProgressIndicator()
                    is LobbyViewModel.State.Success -> {
                        val lobbies = state.lobbies

                        if (lobbies.isEmpty()) {
                            Text(
                                "No lobbies available. Create one!",
                                modifier = Modifier.padding(16.dp)
                            )
                        } else {
                            LobbiesList(
                                lobbies = lobbies,
                                onLobbyClick = { lobby ->
                                    // Handle lobby click - you might want to pass lobby ID
                                    onNavigateToLobby()
                                }
                            )
                        }
                    }

                    is LobbyViewModel.State.Error -> TODO()
                }
            }
            content()
        }
    }
}


@Composable
private fun LobbiesList(
    lobbies: List<Lobby>,
    onLobbyClick: (Lobby) -> Unit
) {
    LazyColumn {
        items(lobbies) { lobby ->
            LobbyItem(
                lobby = lobby,
                onLobbyClick = onLobbyClick,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun LobbyItem(
    lobby: Lobby,
    onLobbyClick: (Lobby) -> Unit,
    modifier: Modifier = Modifier
) {
    SimpleCard(Modifier) {
        Row(
            modifier = modifier
                .clickable { onLobbyClick(lobby) }
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lobby Info
            Column(modifier = Modifier.weight(1f)) {
                TitleText(lobby.name.value,)
                Text(
                    text = lobby.description.value,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Players: ${lobby.expectedPlayers} • Rounds: ${lobby.nOfRounds}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Join indicator or status
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Join lobby"
            )
        }
    }
}