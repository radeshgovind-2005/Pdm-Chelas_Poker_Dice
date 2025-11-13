package isel.pdm.pokerdice.ui.activities.screens.game.statescreen.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.ui.activities.screens.game.GameLayout
import isel.pdm.pokerdice.ui.activities.screens.game.transition.CloseCourtain
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import isel.pdm.pokerdice.ui.viewmodels.GameViewModel

@Composable
fun MatchStartScreen(gvm: GameViewModel, lobby: Lobby) {
    var isCurtainClosed by remember { mutableStateOf(false) }
    GameLayout(
        {BottomRow({ isCurtainClosed = true })}
    ){ PlayersEntrance(lobby.lobbyPlayers)}
    CloseCourtain(isCurtainClosed, gvm)
}

@Composable
private fun BottomRow(onClick: () -> Unit){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        ButtonText(
            text=stringResource(R.string.start_round_btn),
            modifier = Modifier.size(300.dp,50.dp),
            onClick = onClick,
            color = ButtonDefaults.buttonColors(
                containerColor = MediumDarkRed,
                contentColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}