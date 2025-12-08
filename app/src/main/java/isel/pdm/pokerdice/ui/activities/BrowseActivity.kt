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
import isel.pdm.pokerdice.ui.activities.screens.browse.BrowseLobbiesScreen
import isel.pdm.pokerdice.ui.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseNavigation
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseViewModel
import kotlin.getValue

class BrowseActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: BrowseViewModel by viewModels {
        val app = application as HostApp
        BrowseViewModel.getFactory(app.container.browseUseCase)
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
                BrowseLobbiesScreen(
                    state = state,
                    onBackClick = viewmodel::onBackRequest,
                    onQueryChange = viewmodel::onQueryChange,
                    onSearch = viewmodel::onChangeExpand,
                    onCreateLobby = viewmodel::onCreateRequest,
                    onLobbyClick = viewmodel::onLobbyRequest
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
                    BrowseNavigation.ToCreateLobby -> navigateTo(CreateActivity::class.java,false)
                    is BrowseNavigation.ToLobby -> navigateTo(LobbyActivity::class.java){
                        putExtra("LOBBY_ID", effect.lobbyId)
                    }
                    BrowseNavigation.ToTitle -> finish()
                }
            }
        }
    }
}