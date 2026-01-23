package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.main.MainScreen
import isel.pdm.pokerdice.ui.viewmodels.main.MainNavigation
import isel.pdm.pokerdice.ui.viewmodels.main.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: MainViewModel by viewModels {
        val app = application as HostApp
        MainViewModel.getFactory(app.container.mainUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        installSplashScreen()
        enableEdgeToEdge()
        requestNotificationPermission()
        listenForEffects()
        viewmodel.sessionCheck()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                MainScreen(state=state)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        logger.lifeCycle("onResume")
    }

    override fun onPause() {
        super.onPause()
        logger.lifeCycle("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        logger.lifeCycle("onDestroy")
    }


    private fun listenForEffects(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                logger.i("Listening for Effects")
                viewmodel.effects.collect { effect ->
                    logger.i("Effect collected -> ${effect::class.java.simpleName}")
                    when (effect) {
                        MainNavigation.ToLogin -> navigateTo(LoginActivity::class.java)
                        MainNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
                        is MainNavigation.ToLobby -> navigateTo(LobbyActivity::class.java) {
                            putExtra("LOBBY_ID", effect.lobbyId)
                        }
                        is MainNavigation.ToMatch -> navigateTo(MatchActivity::class.java) {
                            putExtra("MATCH_ID", effect.matchId)
                        }
                    }
                }
            }
        }
    }
}
