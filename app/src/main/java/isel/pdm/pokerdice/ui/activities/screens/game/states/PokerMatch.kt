package isel.pdm.pokerdice.ui.activities.screens.game.states

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.ui.activities.screens.game.GameMainLayout
import isel.pdm.pokerdice.ui.activities.screens.game.effects.CloseCurtain
import isel.pdm.pokerdice.ui.activities.screens.game.elements.players.PlayerEntrance
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel

@Composable
fun PokerMatch(mvm: MatchViewModel, lobby: Lobby) {
    var isCurtainClosed by remember { mutableStateOf(false) }
    GameMainLayout(
        bottomRow = {
            Row (
                modifier = Modifier.fillMaxWidth().height((LocalConfiguration.current.screenHeightDp*0.2).dp),
                horizontalArrangement = Arrangement.Center,
            ){
                ButtonText(
                    text= stringResource(R.string.start_round_btn),
                    modifier = Modifier.size(300.dp,50.dp),
                    onClick = { isCurtainClosed = true },
                    color = ButtonDefaults.buttonColors(
                        containerColor = MediumDarkRed,
                        contentColor = MaterialTheme.colorScheme.secondary
                    )
                )
            }
        },
        screenContent = { PlayerEntrance(lobby.lobbyUsers) }
    )
    CloseCurtain(isCurtainClosed, { mvm.matchBegins() })
}