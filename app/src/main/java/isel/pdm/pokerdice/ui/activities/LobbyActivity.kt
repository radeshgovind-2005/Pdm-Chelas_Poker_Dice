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
import isel.pdm.pokerdice.ui.activities.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyNavigation
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyViewModel
import kotlin.getValue

class LobbyActivity: ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: LobbyViewModel by viewModels {
        val app = application as HostApp
        LobbyViewModel.getFactory(app.container.lobbyUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        val lobbyId = intent.getStringExtra("LOBBY_ID")
            ?: throw IllegalArgumentException("Lobby ID is required to start this Activity")
        viewmodel.inititializeVM(lobbyId)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                ListenForEffects()
                LobbyScreen(
                    state = state,
                    onBackClick = viewmodel::onBackRequest,
                    onJoinRequest = viewmodel::onJoinRequest,
                    onStartMatch = viewmodel::onStartRequest
                )
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
                    LobbyNavigation.ToBrowse -> finish()//navigateTo(BrowseActivity::class.java)
                    is LobbyNavigation.ToMatch -> navigateTo(MatchActivity::class.java){
                        putExtra("MATCH_ID", effect.matchId)
                    }
                }
            }
        }
    }
}
