package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.ui.activities.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

class LobbyActivity: NavActivity() {

    private val lobbyViewModel: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.getFactory((application as HostApplication).authUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        authViewModel.getCurrentUser()
        val user = (authViewModel.state as? AuthViewModel.State.LoggedIn)?.user
            ?: User(
                UserCredentials("Guess Invalid","Invalid"),
                AuthInfo(Name.create("Invalid Guess").getOrThrow(),"fake-token")
            )
        intent
            .getStringExtra("LOBBY_ID")
            ?.let{lobbyViewModel.joinLobby(it,user)}
        setContent {
            PokerDiceTheme {
                LobbyScreen(
                    navBack = { navigate(Navigation.OnLobby.GoBack) },
                    navToGame = { navigate(Navigation.OnLobby.ToGame) },
                    lvm = lobbyViewModel,
                    avm = authViewModel
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun navigate(nav: Navigation.OnLobby) =
        when(nav){
            Navigation.OnLobby.GoBack -> finish()
            Navigation.OnLobby.ToGame -> {
                toScreen(GameActivity::class.java,Anim.Forward)
            }
        }
}