package isel.pdm.pokerdice.ui.activities.screens.lobbies

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.components.searchbar.DefaultSearchContentLayout
import isel.pdm.pokerdice.ui.components.text.HeadingLevel
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.components.text.PlainText
import isel.pdm.pokerdice.ui.remember.RememberString
import java.util.UUID

@Composable
fun LobbiesContent(lobbies: Lobbies,navToLobby: (UUID) -> Unit){
    val players = RememberString(R.string.lobbies_content_players)
    val rounds = RememberString(R.string.lobbies_content_rounds)
    DefaultSearchContentLayout(items = lobbies, onClick ={navToLobby(it.id)}){ lobby ->
        Column {
            HeadingText(lobby.name.value, HeadingLevel.H3)
            PlainText(lobby.description.value)
            PlainText("$players: ${lobby.expectedPlayers} • $rounds: ${lobby.nOfRounds}")
        }
        Column(horizontalAlignment = Alignment.End) {
            SimpleIcon(MyIcon.Join)
        }
    }
}