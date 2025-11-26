package isel.pdm.pokerdice.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.LobbiesLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.lobbies.LobbiesScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import java.util.UUID

class LobbiesActivity : MyActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LobbiesLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        lobbyViewModel.loadLobbies()
        setContent {
            PokerDiceTheme {
                LobbiesScreen(
                    navBack = { navigate(Navigation.OnLobbies.GoBack) },
                    navToLobby = { id ->  navigate(Navigation.OnLobbies.ToLobby, id) },
                    navToCreateLobby = { navigate(Navigation.OnLobbies.ToCreateLobby)},
                    viewModel = lobbyViewModel
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        LobbiesLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onResume() {
        super.onResume()
        LobbiesLog.logLifeCycle(getCurrentMethodName())
        //refresh lobbies
    }

    override fun onDestroy() {
        super.onDestroy()
        LobbiesLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnLobbies, lobbyId: UUID? = null) {
        LobbiesLog.logNavigation(nav)
        when (nav) {
            Navigation.OnLobbies.GoBack -> finish()
            Navigation.OnLobbies.ToLobby -> {
                val intent = Intent(this, LobbyActivity::class.java).apply {
                    lobbyId?.let { putExtra("LOBBY_ID", it.toString()) }
                }
                startActivity(intent)
            }

            Navigation.OnLobbies.ToCreateLobby -> toScreen(
                CreateLobbyActivity::class.java,
                Anim.Forward
            )
        }
    }
}