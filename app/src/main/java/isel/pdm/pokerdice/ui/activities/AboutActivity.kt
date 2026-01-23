package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutViewModel
import kotlinx.coroutines.launch

class AboutActivity: ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: AboutViewModel by viewModels { AboutViewModel.getFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        enableEdgeToEdge()
        requestNotificationPermission()
        listenForEffects()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
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


    private fun listenForEffects(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                logger.i("Listening for Effects")
                viewmodel.effects.collect { effect ->
                    logger.i("Effect collected -> ${effect::class.java.simpleName}")
                    when (effect) {
                        AboutNavigation.ToTitle -> finish()
                        is AboutNavigation.ToMail -> navigateToMail(effect.sendTo, effect.subject)
                        is AboutNavigation.ToWeb -> navigateToWeb(effect.uri)
                    }
                }
            }
        }
    }
}
