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
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.create.CreateScreen
import isel.pdm.pokerdice.ui.viewmodels.create.CreateNavigation
import isel.pdm.pokerdice.ui.viewmodels.create.CreateViewModel

class CreateActivity: ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: CreateViewModel by viewModels {
        val app = application as HostApp
        CreateViewModel.provideFactory(
            owner=this,
            usecase =app.container.createUseCase
        )
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
                CreateScreen(
                    state = state,
                    onBackRequest = viewmodel::toBackRequest,
                    onNameChange = viewmodel::onNameChange,
                    onDescriptionChange = viewmodel::onDescriptionChange,
                    onExpectedPlayersChange = viewmodel::onExpectedPlayersChange,
                    onMaxRoundsChange = viewmodel::onMaxRoundsChange,
                    onBalanceChange = viewmodel::onBalanceChange,
                    onAnteChange = viewmodel::onAnteChange,
                    onCreateRequest = viewmodel::onCreateLobby,
                    onTryAgain = viewmodel::onTryAgain
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
                    CreateNavigation.ToBrowse -> navigateTo(BrowseActivity::class.java)
                    is CreateNavigation.ToLobby -> navigateTo(LobbyActivity::class.java){
                        putExtra("LOBBY_ID", effect.lobbyId)
                    }
                }
            }
        }
    }
}