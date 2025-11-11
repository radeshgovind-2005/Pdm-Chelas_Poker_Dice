package isel.pdm.pokerdice.ui.activities.screens.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.Players
import isel.pdm.pokerdice.domain.Users
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.text.HeadingLevel
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.components.text.PlainText
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun LobbyContent(lobby: Lobby, navToGame: () -> Unit,padding: PaddingValues) {
    OneFullColumn( padding,Arrangement.Top) {
        Spacer(modifier = Modifier.height(16.dp))
        DefaultCard(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            LobbyHeader(lobby)
            PlayersSection(lobby.lobbyPlayers)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.Center
                ){
                ButtonText(RememberString(R.string.start_game), MyIcon.Start, onClick = navToGame, enabled = lobby.lobbyPlayers.size == lobby.expectedPlayers.value)
            }
        }
    }
}


@Composable
fun PlayersSection(players: Users){
    Column(modifier = Modifier.padding(16.dp)) {
        HeadingText(RememberString(R.string.players_section_players), HeadingLevel.H3)
        Spacer(modifier = Modifier.height(16.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            players.forEach { player ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimpleIcon(MyIcon.Player, size = 16)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        PlainText(player.authInfo.userName.value)
                    }
                    PlainText(RememberString(R.string.players_section_ready))
                }

            }
        }

    }
}


@Composable
fun LobbyHeader(lobby: Lobby){
    val lobbyName: String = lobby?.name?.value ?: "Null"
    val hostName: String = lobby?.hostName?.value ?: "Null"
    val playerCount: String = lobby?.lobbyPlayers?.size.toString()
    val maxPlayers: String = lobby?.expectedPlayers.toString()
    val rounds: String =lobby?.nOfRounds.toString()


    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeadingText(lobbyName, HeadingLevel.H1)
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            mapOf<String,String>(
                RememberString(R.string.lobby_item_host) to hostName,
                RememberString(R.string.lobby_item_players) to "$playerCount/$maxPlayers",
                RememberString(R.string.lobby_item_rounds) to rounds,
            ).forEach {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PlainText(it.key)
                    PlainText(it.value)
                }
            }

        }

    }
}