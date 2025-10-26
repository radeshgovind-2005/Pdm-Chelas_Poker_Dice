package isel.pdm.chelaspokerdice.screens.lobby.struct

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.services.dto.Lobby
import isel.pdm.chelaspokerdice.ui.components.elements.SmallHeadlineText
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard

@Composable
fun LobbyHeaderCard(lobby: Lobby?) {

    val lobbyName: String = lobby?.name?.value ?: "Null"
    val hostName: String = lobby?.hostName ?: "Null"
    val playerCount: String = lobby?.lobbyPlayers?.size.toString()
    val rounds: String = (playerCount + "/" + lobby?.nOfRounds)
    SimpleCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SmallHeadlineText(lobbyName)
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LobbyInfoItem("Host", hostName)
                LobbyInfoItem("Players", playerCount)
                LobbyInfoItem("Rounds", rounds)
            }
        }

    }
}
