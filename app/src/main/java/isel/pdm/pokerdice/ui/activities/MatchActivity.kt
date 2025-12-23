package isel.pdm.pokerdice.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.match.MatchScreen
import isel.pdm.pokerdice.ui.viewmodels.match.MatchNavigation
import isel.pdm.pokerdice.ui.viewmodels.match.MatchViewModel

class MatchActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: MatchViewModel by viewModels {
        val app = application as HostApp
        MatchViewModel.getFactory(app.container.matchUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        logger.lifeCycle("onCreate")
        val matchId = intent.getStringExtra("MATCH_ID")
            ?: throw IllegalArgumentException("Match ID is required to start this Activity")
        viewmodel.inititializeVM(matchId)
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                ListenForEffects()
                MatchScreen(
                    state = state,
                    onStartRound = viewmodel::onStartRound,
                    onClickDice = viewmodel::onClickDice,
                    onClickPlay = viewmodel::onClickPlay,
                    onClickNext = viewmodel::onClickNext
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
                    is MatchNavigation.ToLobby -> navigateTo(LobbyActivity::class.java){
                        putExtra("LOBBY_ID", effect.lobbyId)
                    }
                    MatchNavigation.ToTitle ->  navigateTo(TitleActivity::class.java)
                }
            }
        }
    }
}
