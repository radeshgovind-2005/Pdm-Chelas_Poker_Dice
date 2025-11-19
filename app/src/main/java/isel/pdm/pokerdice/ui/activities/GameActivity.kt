package isel.pdm.pokerdice.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.ui.activities.screens.game.PokerScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.GameViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

class GameActivity: NavActivity() {
    private val lobbyViewModel: LobbyViewModel by viewModels {
        LobbyViewModel.getFactory((application as HostApplication).lobbyService)
    }
    private val gameViewModel: GameViewModel by viewModels {
        GameViewModel.getFactory((application as HostApplication).gameService)
    }

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.getFactory((application as HostApplication).authUseCase)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("GameActivity","GameActivity: Created")
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        gameViewModel.initializeMatch()
        setContent {
            PokerDiceTheme {
                PokerScreen(gameViewModel,lobbyViewModel,authViewModel)
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        Log.d("GameActivity","GameActivity: Destroyed")
    }
    private fun navigate(nav: Navigation.OnLobby) =
        when(nav){
            Navigation.OnLobby.GoBack -> finish()
            Navigation.OnLobby.ToGame -> toScreen(TitleActivity::class.java,Anim.Forward)
        }
}