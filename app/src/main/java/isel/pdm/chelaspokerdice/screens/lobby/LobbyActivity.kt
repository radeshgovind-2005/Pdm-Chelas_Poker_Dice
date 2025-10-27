package isel.pdm.chelaspokerdice.screens.lobby

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.HostApplication
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.screens.game.GameActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme
import isel.pdm.chelaspokerdice.vm.LobbyViewModel
import kotlin.getValue

class LobbyActivity: ActivityNavigator() {

    private val lobbyVm: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val lobbyId = intent.getStringExtra("LOBBY_ID")

        Log.d("LobbyActivity", "Received LOBBY_ID: $lobbyId")
        lobbyId?.let {
            lobbyVm.loadLobby(it)
        }
        setContent {
            ChelasPokerDiceTheme {
                val currentState = lobbyVm.state
                Log.d("LobbyActivity", "UI State: $currentState")
                LobbyScreen(
                    onNavigateBack = { navigate(LobbyNavigation.ToHome) },
                    onNavigateGame = { navigate(LobbyNavigation.ToGame) },
                    lobbyViewModel = lobbyVm
                ).Render(Modifier)
            }
        }

        lobbyId?.let {
            Log.d("LobbyActivity", "Loading lobby with ID: $it")
            lobbyVm.loadLobby(it)
        } ?: run {
            Log.e("LobbyActivity", "No LOBBY_ID found in intent!")
        }
    }
    override fun onPause() {
        super.onPause()
        lobbyVm.cancelOperations()
    }

    private fun navigate(nav: LobbyNavigation) {
        when (nav) {
            LobbyNavigation.ToGame -> navigationToScreen(GameActivity::class.java, Anim.Backwards)
            LobbyNavigation.ToHome -> navigationToScreen(MainActivity::class.java, Anim.Backwards)
        }
    }
}