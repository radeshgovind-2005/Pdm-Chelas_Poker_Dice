package isel.pdm.pokerdice.ui.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.LobbyLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.navigation.Navigation
import java.util.UUID

class LobbyActivity: MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LobbyLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        lobbyViewModel.startPolling()
        setContent {
            SessionVerification(
                authenticatedScreen = { user ->
                    LoadVms()
                    LobbyScreen(
                        navBack = { navigate(Navigation.OnLobby.GoBack) },
                        navToGame = { navigate(Navigation.OnLobby.ToGame(it)) },
                        lvm = lobbyViewModel,
                        avm = authViewModel,
                        gvm = gameViewModel
                    )
                }
            )
        }
        checkNotificationPermission()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    private fun LoadVms() {
        intent
            .getParcelableExtra("LOBBY_ID",UUID::class.java)
            ?.let { lobbyViewModel.joinLobby(it, user) }
    }

    override fun onPause() {
        super.onPause()
        LobbyLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onResume() {
        super.onResume()
        LobbyLog.logLifeCycle(getCurrentMethodName())
        // TEMPORARY TEST: Trigger notification 5 seconds after opening screen
        // Remove this before final submission!
        window.decorView.postDelayed({
            (application as HostApplication)
                .notificationSource
                .showGameStartedNotification("Test Lobby")
        }, 5000)
    }

    override fun onDestroy() {
        super.onDestroy()
        LobbyLog.logLifeCycle(getCurrentMethodName())
    }
    private fun navigate(nav: Navigation.OnLobby) {
        LobbyLog.logNavigation(nav)
        nav.dest
            ?.let {
                if(nav is Navigation.OnLobby.ToGame)
                    toClearScreen(it){
                        putExtra("MATCH_EXTRA", nav.match)
                    }
            }
            ?: finish()
    }
}