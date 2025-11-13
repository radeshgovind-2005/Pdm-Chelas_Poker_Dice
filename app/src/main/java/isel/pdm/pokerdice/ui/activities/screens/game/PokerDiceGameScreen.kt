package isel.pdm.pokerdice.ui.activities.screens.game

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.activities.screens.game.statescreen.match.MatchStartScreen
import isel.pdm.pokerdice.ui.activities.screens.game.statescreen.round.RoundScreen
import isel.pdm.pokerdice.ui.activities.screens.pd2.GameScreenBox
import isel.pdm.pokerdice.ui.activities.screens.pd2.table.PokerDiceTable
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.GameViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

@Composable
fun PokerDiceGameScreen(gvm: GameViewModel, lvm: LobbyViewModel, avm: AuthViewModel){
    avm.getCurrentUser()?.let{ currUser ->
        lvm.getCurrentLobby(currUser)?.let{ currLobby ->
            when(gvm.state){
                is GameViewModel.State.Error -> TODO()
                GameViewModel.State.Idle -> TODO()
                GameViewModel.State.MatchInit -> { MatchStartScreen(gvm,currLobby) }
                is GameViewModel.State.PlayingInRound -> { RoundScreen(currLobby) }
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