package isel.pdm.pokerdice.ui.activities.screens.pd3

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.activities.screens.pd3.statescreen.match.MatchStartScreen
import isel.pdm.pokerdice.ui.activities.screens.pd3.statescreen.round.RoundScreen
import isel.pdm.pokerdice.ui.activities.screens.pd2.GameScreenBox
import isel.pdm.pokerdice.ui.activities.screens.pd2.table.PokerDiceTable
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

@Composable
fun PokerDiceGameScreen(gvm: MatchViewModel, lvm: LobbyViewModel, avm: AuthViewModel){
    avm.getCurrentUser()?.let{ currUser ->
        lvm.getCurrentLobby(currUser)?.let{ currLobby ->
            when(gvm.state){
                is MatchViewModel.State.Error -> TODO()
                MatchViewModel.State.Idle -> TODO()
                //MatchViewModel.State.MatchInit -> { MatchStartScreen(gvm,currLobby) }
                //MatchViewModel.State.MatchRunning -> { RoundScreen(currLobby) }
                is MatchViewModel.State.MatchInit -> TODO()
                MatchViewModel.State.MatchLoading -> TODO()
                is MatchViewModel.State.MatchRunning -> TODO()
                is MatchViewModel.State.MatchCreated -> TODO()
                MatchViewModel.State.MatchCreating -> TODO()
            }
        } ?:  Log.d("APP_INFO", "Could not get current Lobby on Game Screen")
    } ?: Log.d("APP_INFO", "Could not get current user on Game Screen")

}
@Composable
fun GameLayout(
    bottomRow: @Composable () -> Unit,
    upperRow: @Composable () -> Unit = {},
    tableContent: @Composable (PaddingValues) -> Unit = {},
    screenContent: @Composable () -> Unit = {}
){
    Scaffold(
        topBar = { upperRow() },
        floatingActionButton = { bottomRow() }
    ) { paddingValues ->
        GameScreenBox{
            PokerDiceTable{
                tableContent(paddingValues)
            }
            screenContent()
        }
    }
}