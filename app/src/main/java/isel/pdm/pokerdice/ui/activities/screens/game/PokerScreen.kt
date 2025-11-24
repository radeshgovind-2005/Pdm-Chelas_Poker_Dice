package isel.pdm.pokerdice.ui.activities.screens.game

import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.GameLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerError
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerGame
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerIdle
import isel.pdm.pokerdice.ui.activities.screens.game.states.PokerMatch
import isel.pdm.pokerdice.ui.components.progressindicator.DefaultCircularProgressIndicator
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel


@Composable
fun PokerScreen(
    mvm: MatchViewModel,
    lvm: LobbyViewModel,
    avm: AuthViewModel
) {
    val methodName = getCurrentMethodName()
    avm.getCurrentUser()?.let{ currUser ->
        lvm.getCurrentLobby(currUser)?.let{ currLobby ->
            GameLog.logVm(mvm,methodName)
            when(mvm.state){
                is MatchViewModel.State.Error -> PokerError()
                MatchViewModel.State.Idle -> PokerIdle()
                MatchViewModel.State.MatchLoading -> DefaultCircularProgressIndicator()
                is MatchViewModel.State.MatchInit -> PokerMatch(mvm,currLobby)
                is MatchViewModel.State.MatchRunning -> PokerGame(mvm,currLobby,currUser)
                is MatchViewModel.State.MatchCreated -> DefaultCircularProgressIndicator()
                MatchViewModel.State.MatchCreating -> DefaultCircularProgressIndicator()
            }
        } ?:  GameLog.logVm(lvm,methodName,"Lobby ViewModel is null")
    } ?: GameLog.logVm(avm,methodName,"Auth ViewModel is null")
}
