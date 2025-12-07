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
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.activities.screens.main.MainScreen
import isel.pdm.pokerdice.ui.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.viewmodels.main.MainNavigation
import isel.pdm.pokerdice.ui.viewmodels.main.MainViewModel
import isel.pdm.pokerdice.ui.viewmodels.title.TitleNavigation

class MainActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: MainViewModel by viewModels {
        val app = application as HostApp
        MainViewModel.getFactory(app.container.mainUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                ListenForEffects()
                MainScreen(state=state)
                LaunchedEffect(Unit) {
                    viewmodel.sessionCheck()
                }
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

    @Composable
    private fun ListenForEffects(){
        logger.i("Listening for Effects")
        LaunchedEffect(Unit) {
            viewmodel.effects.collect { effect ->
                logger.i("Effect collected -> ${effect::class.java.simpleName}")
                when (effect) {
                    MainNavigation.ToLogin -> navigateTo(LoginActivity::class.java)
                    MainNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
                    is MainNavigation.ToLobby -> navigateTo(LobbyActivity::class.java){
                        putExtra("LOBBY_ID", effect.lobbyId)
                    }
                    is MainNavigation.ToMatch -> navigateTo(MatchActivity::class.java){
                        putExtra("MATCH_ID", effect.matchId)
                    }
                }
            }
        }
    }
}
