package isel.pdm.pokerdice.ui.activities.screens.match.round

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.Dices
import isel.pdm.pokerdice.domain.Player
import isel.pdm.pokerdice.domain.Round
import isel.pdm.pokerdice.ui.layouts.background.DarkRedBackground
import isel.pdm.pokerdice.ui.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.theme.tableBrush
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PokerTable(
    state: MatchState,
    onClickButton: () -> Unit
) {
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp
    Scaffold(
        bottomBar = { MatchBottomBar(state,onClickButton) },
        topBar = { MatchTopBar(state) }
    ) {
        DarkRedBackground {
            val tableWidth = screenWidth * 0.8f
            val tableHeight = screenHeight * 0.33f *2
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(tableWidth)
                        .height(tableHeight)
                        .border(width = 8.dp, color = Color.Black, shape = CircleShape)
                        .background(brush = tableBrush, shape = CircleShape)
                )
                PlayersPosition(state)
            }
        }
    }
    PokerDice(state,screenHeight)
}

@Composable
private fun MatchTopBar(state: MatchState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Balance: ${state.game?.round?.players?.firstOrNull{it.name==state.game?.username}?.balance }$")
        Text("Prize: ${state.game?.round?.roundBet}$")
    }
}

@Composable
private fun MatchBottomBar(state: MatchState, onClickButton: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClickButton,
            enabled = state.username == state.round?.turn,
            modifier = Modifier.padding(8.dp)
        ) {
            val game = state.game
            val round = game?.round
            val txt = when{
                round == null -> ""
                //state.rerollsLeft == 3 -> "Roll All Dices"
                //state.dices.roundHand.all { it.isSelected }-> "Hold All"
                //state.rerollsLeft > 0 -> "Re-Roll Unselected Dices"
                else -> ""
            }
            Text(txt)
        }
    }
}

//@Preview(
//    showBackground = true,
//    device = "spec:parent=pixel_3a,orientation=landscape"
//)
//@Composable
//fun previewPokerTable(){
//    val lobby = null
//    val round = null
//    PokerdiceTheme {
//        PokerTable(state, {})
//    }
//}