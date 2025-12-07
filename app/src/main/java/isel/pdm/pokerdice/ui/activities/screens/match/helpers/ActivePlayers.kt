package isel.pdm.pokerdice.ui.activities.screens.match.helpers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.match.Game
import isel.pdm.pokerdice.domain.match.MatchPlayers

@Composable
fun ActivePlayers(game: Game) {
    val players = game.round?.players ?: return
    Column(
        modifier = Modifier.fillMaxWidth(0.95f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = 16.dp),
        ) {
            if(players.size<1) return
            Row(
                modifier=Modifier.fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.Start
            ){

                PlayerSeat(players[0])
            }
            if(players.size<2) return
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                PlayerSeat(players[1])
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        ) {
            if(players.size<3) return
            Row(
                modifier=Modifier.fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.Start
            ){
                PlayerSeat(players[2])
            }
            if(players.size<4) return
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                PlayerSeat(players[3])
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = 16.dp),
        ) {
            if(players.size<5) return
            Row(
                modifier=Modifier.fillMaxWidth(0.5f),
                horizontalArrangement = Arrangement.Start
            ){
                PlayerSeat(players[4])
            }
            if(players.size<6) return
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                PlayerSeat(players[5])
            }
        }

    }
}
@Composable
private fun PlayerSeat(player: MatchPlayers){
    val cardColor = if(player.state == "turn")Color.Green.copy(alpha = 0.4f) else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
    Card(
        modifier=Modifier,
        shape = RoundedCornerShape(24.dp),
        colors =  CardDefaults.cardColors(
            containerColor = cardColor,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier=Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row{ Text(player.name) }
            Row{
                player.hand.forEach { face ->
                    Card(
                        modifier=Modifier.padding(horizontal=1.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors =  CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Box(Modifier.padding(horizontal=2.dp)){
                            Text(face.toString())
                        }
                    }
                }
            }
        }
    }
}
