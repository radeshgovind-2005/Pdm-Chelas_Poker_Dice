    package isel.pdm.pokerdice.ui.activities.screens.match.round

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.Dices
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState

@Composable
fun PokerDice(state: MatchState,screenHeight: Dp) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val isNotTurn = state.username != state.round?.turn
        val diceColor = if(isNotTurn) Color.LightGray else Color.White
        val txtColor = if(isNotTurn) Color.DarkGray else Color.Black
        Row {
            repeat(5) { time ->
                val dice = state.round?.hand?.roundHand[time]
                val isSelected = dice?.isSelected == true
                val broder = if(isSelected) 3.dp else 1.dp
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(screenHeight / 8)
                        .background(color = diceColor, shape = RoundedCornerShape(15.dp))
                        .border(
                            width = broder,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable(
                            onClick = {dice?.copy(isSelected = isSelected)}
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    val font = if(isSelected) FontWeight.ExtraBold else FontWeight.ExtraLight
                    val symb = when{
                        isSelected -> dice?.face?.symb.toString()
                        !isSelected && state.round?.isRolling == true -> Dices.randomFace.symb
                        else -> "?"
                    }
                    Text(symb, color = txtColor, fontWeight = font)
                }
            }
        }
        val rerolls = state.round?.rerollCounter ?: return
        Row(Modifier.padding(vertical = 2.dp)){
            Text("Re-Rolls: x$rerolls", color = MaterialTheme.colorScheme.primary)
        }
    }
}