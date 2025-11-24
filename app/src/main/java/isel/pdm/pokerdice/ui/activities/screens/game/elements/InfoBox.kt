package isel.pdm.pokerdice.ui.activities.screens.game.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.Match
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.text.HeadingLevel
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.components.text.PlainText
import isel.pdm.pokerdice.ui.theme.DarkWhite

@Composable
fun InfoBox(lobby: Lobby,match: Match) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DefaultCard(
            modifier = Modifier.fillMaxSize(0.85f),
            hca = Alignment.CenterHorizontally,
            sufVar = 1f
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.Center
            ){
                HeadingText("${lobby.name}", color = DarkWhite)
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                PlainText("Round: ${match.round}/${lobby.nOfRounds}")
                PlainText("Ante: ${lobby.ante}$")
                PlainText("Match Players: ${match.players.map { it.user.userCredentials.username }}")
                PlainText("Prize: ${match.prize}")
                PlainText("Turn: ${match.turn?.user?.userCredentials?.username}")
            }
        }
    }
}