package isel.pdm.chelaspokerdice.screens.lobbies

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.HostApplication
import isel.pdm.chelaspokerdice.screens.about.AboutActivity
import isel.pdm.chelaspokerdice.screens.createlobby.CreateLobbyActivity
import isel.pdm.chelaspokerdice.screens.lobby.LobbyActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbiesNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme
import isel.pdm.chelaspokerdice.vm.LobbyViewModel
import kotlin.getValue

class LobbiesActivity : ActivityNavigator() {

    private val lobbyVm: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LobbiesScreen(
                    onNavigateToTitleScreen = { navigate(LobbiesNavigation.ToTitleScreen) },
                    onNavigateToCreateLobby = { navigate(LobbiesNavigation.ToCreateLobby) },
                    onNavigateToLobby = { lobbyId -> navigate(LobbiesNavigation.ToLobby, lobbyId) },
                    lobbyViewModel = lobbyVm
                ).Render(Modifier)
            }
        }
    }
    override fun onResume() {
        super.onResume()
        // Add delay to prevent rapid retries during configuration changes
        Handler(Looper.getMainLooper()).postDelayed({
            lobbyVm.loadLobbies()
        }, 500) // 500ms delay
    }

    override fun onPause() {
        super.onPause()
        lobbyVm.cancelOperations()
    }

    private fun navigate(nav: LobbiesNavigation, lobbyId: String? = null) {
        when (nav) {
            LobbiesNavigation.ToTitleScreen -> finish()
            LobbiesNavigation.ToCreateLobby -> navigationToScreen(CreateLobbyActivity::class.java, Anim.Forward)
            LobbiesNavigation.ToLobby -> {
                val intent = Intent(this, LobbyActivity::class.java).apply {
                    lobbyId?.let { putExtra("LOBBY_ID", it) }
                }
                startActivity(intent)
            }
        }
    }
}