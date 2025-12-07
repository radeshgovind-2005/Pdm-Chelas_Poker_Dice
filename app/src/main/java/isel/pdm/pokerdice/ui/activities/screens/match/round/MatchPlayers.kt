package isel.pdm.pokerdice.ui.activities.screens.match.round

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
import isel.pdm.pokerdice.domain.Player
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState


@Composable
fun PlayersPosition(state: MatchState) {
    val players = state.round?.players ?: return
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

                PlayerBox(players[0],state.round.turn)
            }
            if(players.size<2) return
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                PlayerBox(players[1],state.round.turn)
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
                PlayerBox(players[2],state.round.turn)
            }
            if(players.size<4) return
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                PlayerBox(players[3],state.round.turn)
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
                PlayerBox(players[4],state.round.turn)
            }
            if(players.size<6) return
            Row(
                modifier=Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                PlayerBox(players[5],state.round.turn)
            }
        }

    }
}

@Composable
private fun PlayerBox(player: Player, turn: String?){
    val cardColor = if(turn == player.name)Color.Green.copy(alpha = 0.4f) else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f)
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
            Row{
                Text(player.name)
            }
            Row{
                player.hand.roundHand.forEach {
                    val face = if(player.hasPlayed) it.face.symb else " "
                    Card(
                        modifier=Modifier.padding(horizontal=1.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors =  CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Box(Modifier.padding(horizontal=2.dp)){
                            Text(face)
                        }
                    }
                }
            }
        }
    }
}
