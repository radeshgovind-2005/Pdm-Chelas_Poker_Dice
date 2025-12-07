package isel.pdm.pokerdice.ui.activities.screens.match.otherViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.match.Game
import isel.pdm.pokerdice.ui.components.card.SimpleCard
import isel.pdm.pokerdice.ui.components.text.BoldTitle
import isel.pdm.pokerdice.ui.layouts.background.DarkRedBackground

@Composable
fun AnounceRoundWinner(game: Game, onClickNext: () -> Unit) {
    val btnResId by rememberSaveable { mutableIntStateOf(R.string.match_next_btn) }
    val lobbyName = game.lobby.name
    val msg = game.msg.toString()
    DarkRedBackground {
        SimpleCard {
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                BoldTitle( lobbyName)
                Spacer(Modifier.height(32.dp))
                Text(msg)
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = onClickNext,
                    modifier = Modifier.fillMaxWidth(0.65f)
                ) {
                    Text(stringResource(btnResId))
                }
            }
        }
    }
}