package isel.pdm.pokerdice.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.CreateLobbyLog
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.LobbiesLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.createlobby.CreateLobbyScreen
import isel.pdm.pokerdice.ui.activities.screens.lobbies.LobbiesScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import java.util.UUID

class CreateLobbyActivity: MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CreateLobbyLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        authViewModel.getCurrentUser()
        setContent {
            SessionVerification(
                authenticatedScreen = { user ->
                    CreateLobbyScreen(
                        navBack = { navigate(Navigation.OnCreateLobby.GoBack) },
                        navToLobby = { id ->  navigate(Navigation.OnCreateLobby.ToLobby, id) },
                        lvm = lobbyViewModel,
                        avm = authViewModel
                    )
                }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        CreateLobbyLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onResume() {
        super.onResume()
        CreateLobbyLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onDestroy() {
        super.onDestroy()
        CreateLobbyLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnCreateLobby, lobbyId: UUID? = null) {
        CreateLobbyLog.logNavigation(nav)
        nav.dest?.let{ destiny ->
            toScreen(destiny){
                lobbyId
                    ?.let { putExtra("LOBBY_UUID", it) }
                    ?: LobbiesLog.logDebug("Lobby Id is Null")
            }
        }
        finish()
    }
}