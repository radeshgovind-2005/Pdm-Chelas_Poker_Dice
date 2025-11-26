package isel.pdm.pokerdice.ui.activities

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.GameLog
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.Match
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.game.PokerScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import java.lang.Thread.sleep

class GameActivity: MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GameLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        reloadVms()
        setContent {
            PokerDiceTheme {
                PokerScreen(gameViewModel,lobbyViewModel,authViewModel)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        GameLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onResume() {
        super.onResume()
        GameLog.logLifeCycle(getCurrentMethodName())
    }

    override fun onDestroy() {
        super.onDestroy()
        GameLog.logLifeCycle(getCurrentMethodName())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }
    private fun navigate(nav: Navigation.OnGame) {
        GameLog.logNavigation(nav)
    }

    private fun reloadVms(){
        gameViewModel.firstStart()
        val matchFromIntent: Match? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("MATCH_EXTRA", Match::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("MATCH_EXTRA")
        }

        if (matchFromIntent != null) {
            gameViewModel.loadMatch(matchFromIntent)
        }
    }
}