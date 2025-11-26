package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import isel.pdm.pokerdice.LobbiesLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.lobbies.LobbiesScreen
import isel.pdm.pokerdice.ui.navigation.Navigation
import java.util.UUID

class LobbiesActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LobbiesLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        lobbyViewModel.loadLobbies()
        setContent {
            SessionVerification(
                authenticatedScreen = { user ->
                    LobbiesScreen(
                        navBack = { navigate(Navigation.OnLobbies.GoBack) },
                        navToLobby = { id ->  navigate(Navigation.OnLobbies.ToLobby, id) },
                        navToCreateLobby = { navigate(Navigation.OnLobbies.ToCreateLobby)},
                        viewModel = lobbyViewModel
                    )
                }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        LobbiesLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onResume() {
        super.onResume()
        LobbiesLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onDestroy() {
        super.onDestroy()
        LobbiesLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnLobbies, lobbyId: UUID? = null) {
        LobbiesLog.logNavigation(nav)
        nav.dest
            ?.let{destiny->
                when{
                    nav == Navigation.OnLobbies.ToLobby -> {
                        toScreen(destiny){
                            lobbyId?.let {
                                putExtra("LOBBY_UUID", it)
                            } ?: LobbiesLog.logDebug("Lobby Id is Null")
                        }
                    }
                    nav == Navigation.OnLobbies.ToCreateLobby -> {
                        toScreen(destiny)
                    }
                }
            }
            ?: finish()
    }
}