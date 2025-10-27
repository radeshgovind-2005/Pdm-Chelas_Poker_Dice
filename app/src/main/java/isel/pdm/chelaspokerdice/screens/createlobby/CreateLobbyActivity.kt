package isel.pdm.chelaspokerdice.screens.createlobby

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.HostApplication
import isel.pdm.chelaspokerdice.screens.lobby.LobbyActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.CreateLobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme
import isel.pdm.chelaspokerdice.vm.LobbyViewModel
import kotlin.getValue

class CreateLobbyActivity: ActivityNavigator() {

    private val lobbyVm: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                CreateLobbyScreen(
                    onNavigateToLobbies = { navigate(CreateLobbyNavigation.ToLobbies) },
                    onNavigateToLobby = { lobbyId -> navigate(CreateLobbyNavigation.ToLobby,  lobbyId) },
                    lobbyViewModel = lobbyVm
                ).Render(Modifier)
            }
        }
    }
    override fun onPause() {
        super.onPause()
        lobbyVm.cancelOperations()
    }

    private fun navigate(nav: CreateLobbyNavigation, lobbyId: String? = null) {
        when (nav) {
            CreateLobbyNavigation.ToLobbies -> finish()
            CreateLobbyNavigation.ToLobby -> {
                // Get the current lobby from ViewModel state
                val currentState = lobbyVm.state
                Log.d("CreateLobbyActivity", "Current state: $currentState")
                if (currentState is LobbyViewModel.State.InLobby) {
                    val intent = Intent(this, LobbyActivity::class.java).apply {
                        // Use the lobby ID from the state, not the parameter
                        putExtra("LOBBY_ID", currentState.lobby.id.toString())
                    }
                    startActivity(intent)
                } else {
                    // Fallback if no lobby in state
                    navigationToScreen(LobbyActivity::class.java, Anim.Forward)
                }
            }
        }
    }
}