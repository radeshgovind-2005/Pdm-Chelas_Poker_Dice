package isel.pdm.pokerdice.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.ui.activities.screens.lobbies.LobbiesScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import java.util.UUID

class LobbiesActivity : NavActivity() {
    private val lobbyViewModel: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    override fun onResume() {
        super.onResume()
        //lobbyViewModel.refreshLobbies()
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up if needed
    }

    private fun navigate(nav: Navigation.OnLobbies, lobbyId: UUID? = null) =
        when(nav){
            Navigation.OnLobbies.GoBack -> finish()
            Navigation.OnLobbies.ToLobby -> {
                val intent = Intent(this, LobbyActivity::class.java).apply {
                    lobbyId?.let { putExtra("LOBBY_ID", it.toString()) }
                }
                startActivity(intent)
            }
            Navigation.OnLobbies.ToCreateLobby -> toScreen(CreateLobbyActivity::class.java,Anim.Forward)
        }
}