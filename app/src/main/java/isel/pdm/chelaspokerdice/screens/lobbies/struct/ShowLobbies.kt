package isel.pdm.chelaspokerdice.screens.lobbies.struct

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.services.dto.Lobby
import isel.pdm.chelaspokerdice.ui.components.elements.MediumText
import isel.pdm.chelaspokerdice.ui.components.elements.SmallText
import isel.pdm.chelaspokerdice.ui.components.elements.TitleText
import isel.pdm.chelaspokerdice.ui.components.figures.icons.JoinIcon
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard
import isel.pdm.chelaspokerdice.vm.LobbyViewModel

@Composable
fun ShowLobbies(state: LobbyViewModel.State.LobbiesLoaded, onNavigateToLobby:(Lobby) -> Unit) {
    val lobbies = state.lobbies
    if (lobbies.isEmpty()) {
        Text("No lobbies available. Create one!", Modifier.padding(16.dp))
    } else {
        LazyColumn {
            items(lobbies) { lobby ->
                LobbyItem(
                    lobby = lobby,
                    onLobbyClick = { onNavigateToLobby(lobby) },
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                )
            }
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
            modifier.clickable { onLobbyClick(lobby) }.padding(horizontal = 16.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                TitleText(lobby.name.value)
                MediumText(lobby.description.value)
                SmallText("Players: ${lobby.expectedPlayers} • Rounds: ${lobby.nOfRounds}")
            }
            JoinIcon()
        }
    }
}