package isel.pdm.pokerdice.ui.screens.match.otherViews

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.model.match.Game
import isel.pdm.pokerdice.ui.common.layouts.background.DarkRedBackground
import isel.pdm.pokerdice.ui.common.theme.tableBrush
import isel.pdm.pokerdice.ui.screens.match.helpers.ActivePlayers
import isel.pdm.pokerdice.ui.screens.match.helpers.PokerDices
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PlayView(state: MatchState, onClickDice: (Int) -> Unit, onClickBtn: () -> Unit) {
    val game = state.game ?: return
    val config = LocalConfiguration.current
    val screenWidth = config.screenWidthDp.dp
    val screenHeight = config.screenHeightDp.dp
    Scaffold(
        bottomBar = { MatchBottomBar(state, onClickBtn) },
        topBar = { MatchTopBar(game) }
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
                ActivePlayers(game)
            }
        }
    }
    PokerDices(state,screenHeight,onClickDice)
}

@Composable
private fun MatchTopBar(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("${stringResource(R.string.match_balance)}: ${game.round?.players?.firstOrNull{it.name==game.username}?.balance }$")
        Text("${stringResource(R.string.match_prize)}: ${game.round?.roundBet}$")
    }
}

@Composable
private fun MatchBottomBar(state: MatchState, onClickButton: () -> Unit) {
    val game = state.game ?: return
    val isUserTurn = game.username == game.round?.players?.firstOrNull{it.state=="turn"}?.name
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClickButton,
            enabled = isUserTurn,
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                if(state.currHand.value.all{it.isSelected} ||state.currTurn?.rerollsLeft == 0)
                    stringResource(R.string.match_hold_btn)
                else stringResource(R.string.match_roll_btn)
            )
        }
    }
}