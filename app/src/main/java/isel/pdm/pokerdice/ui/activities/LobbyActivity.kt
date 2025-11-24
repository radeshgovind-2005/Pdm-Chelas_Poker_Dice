package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

class LobbyActivity: NavActivity() {

    private val lobbyViewModel: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.getFactory((application as HostApplication).authUseCase)
    }

    private val gameViewModel: MatchViewModel by viewModels {
        MatchViewModel.getFactory((application as HostApplication).gameService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LobbyLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        loadVms()
        setContent {
            PokerDiceTheme {
                LobbyScreen(
                    navBack = { navigate(Navigation.OnLobby.GoBack) },
                    navToGame = { navigate(Navigation.OnLobby.ToGame(it)) },
                    lvm = lobbyViewModel,
                    avm = authViewModel,
                    gvm = gameViewModel
                )
            }
        }
    }

    private fun loadVms() {
        authViewModel.getCurrentUser()
        val user = (authViewModel.state as? AuthViewModel.State.LoggedIn)?.user
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