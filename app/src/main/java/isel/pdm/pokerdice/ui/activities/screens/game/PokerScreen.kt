package isel.pdm.pokerdice.ui.activities.screens.game

import android.util.Log
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.AppTag
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerError
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerIdle
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerMatch
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerGame
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.GameViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

@Composable
fun PokerScreen(
    gvm: GameViewModel,
    lvm: LobbyViewModel,
    avm: AuthViewModel
) {
    avm.getCurrentUser()?.let{ currUser ->
        lvm.getCurrentLobby(currUser)?.let{ currLobby ->
            when(gvm.state){
                is GameViewModel.State.Error -> PokerError()
                GameViewModel.State.Idle -> PokerIdle()
                GameViewModel.State.MatchInit -> PokerMatch(gvm,currLobby)
                is GameViewModel.State.PlayingInRound -> PokerGame(currLobby,currUser)
            }
        } ?:  Log.d(AppTag.Error.value, "Could not get current Lobby on Poker Screen")
    } ?: Log.d(AppTag.Error.value, "Could not get current user on Game Screen")
}