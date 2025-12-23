package isel.pdm.pokerdice.ui.screens.match.helpers

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
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState

//todo select
@Composable
fun PokerDices(
    state: MatchState,
    screenHeight: Dp,
    onClickDice: (Int) -> Unit
) {
    val game = state.game
    val isUserTurn =  state.currTurn?.name == game?.username
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val diceColor = if(!isUserTurn) Color.LightGray else Color.White
        val txtColor = if(!isUserTurn) Color.DarkGray else Color.Black
        Row{
            state.currHand.value.forEachIndexed {idx, dice ->
                val widthBorder = if(dice.isSelected) 3.dp else 1.dp
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(screenHeight / 8)
                        .background(color = diceColor, shape = RoundedCornerShape(15.dp))
                        .border(
                            width = widthBorder,
                            color = Color.Black,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable(
                            onClick = {onClickDice(idx) }
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(dice.face.symb, color = txtColor, fontWeight =  if(dice.isSelected) FontWeight.ExtraBold else FontWeight.Normal)
                }
            }
        }
        if(state.currTurn == null) return
        Row{
            Text("Re-Rolls: X${state.currTurn.rerollsLeft}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.ExtraBold)
        }
    }
}