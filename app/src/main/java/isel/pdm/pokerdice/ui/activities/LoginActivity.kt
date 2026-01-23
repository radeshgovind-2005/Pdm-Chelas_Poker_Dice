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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.login.LoginScreen
import isel.pdm.pokerdice.ui.viewmodels.login.LoginNavigation
import isel.pdm.pokerdice.ui.viewmodels.login.LoginViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class LoginActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: LoginViewModel by viewModels {
        val app = application as HostApp
        LoginViewModel.provideFactory(
            owner=this,
            usecase = app.container.authUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        enableEdgeToEdge()
        requestNotificationPermission()
        listenForEffects()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                LoginScreen(
                    state = state,
                    onUsernameChange = viewmodel::onUsernameChange,
                    onPasswordChange = viewmodel::onPasswordChange,
                    onLoginClick = viewmodel::signIn
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
                        LoginNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
                    }
                }
            }
        }
    }
}
