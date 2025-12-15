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
import isel.pdm.pokerdice.ui.activities.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutViewModel
import kotlin.getValue

class AboutActivity: ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: AboutViewModel by viewModels { AboutViewModel.getFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                ListenForEffects()
                AboutScreen(
                    state = state,
                    onBackClick = viewmodel::onBackRequest,
                    onMailClick = viewmodel::onMailRequest,
                    onWebRequest = viewmodel::onWebRequest
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
                    AboutNavigation.ToTitle -> finish()
                    is AboutNavigation.ToMail -> navigateToMail(effect.sendTo,effect.subject)
                    is AboutNavigation.ToWeb -> navigateToWeb(effect.uri)
                }
            }
        }
    }
}
