package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.LobbyLog
import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel

class LobbyActivity: MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LobbyLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        lobbyViewModel.startPolling()
        setContent {
            PokerDiceTheme {
                LoadVms()
                LobbyScreen(
                    navBack = { navigate(Navigation.OnLobby.GoBack) },
                    navToGame = { navigate(Navigation.OnLobby.ToGame(it)) },
                    lvm = lobbyViewModel,
                    avm = authViewModel,
                    gvm = gameViewModel
                )
            }
        }
        checkNotificationPermission()
    }

    @Composable
    private fun LoadVms() {
        authViewModel.getCurrentUser()
        val avmState by authViewModel.state.collectAsState()
        val user = (avmState as? AuthViewModel.State.LoggedIn)?.user
            ?: User(
                UserCredentials("Guess Invalid", "Invalid"),
                AuthInfo(Name.create("Invalid Guess").getOrThrow(), "fake-token")
            )
        intent
            .getStringExtra("LOBBY_ID")
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
        when (nav) {
            Navigation.OnLobby.GoBack -> finish()
            is Navigation.OnLobby.ToGame -> {
                toClearScreen(GameActivity::class.java, Anim.Forward){ intent ->
                    intent.putExtra("MATCH_EXTRA", nav.match)
                }
            }
        }
    }
}