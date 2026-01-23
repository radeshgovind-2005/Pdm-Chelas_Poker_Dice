package isel.pdm.pokerdice.ui.activities

import android.content.Intent
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
import isel.pdm.pokerdice.ui.screens.profile.ProfileScreen
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileNavigation
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: ProfileViewModel by viewModels {
        val app = application as HostApp
        ProfileViewModel.getFactory(app.container.profileUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        viewmodel.onCreateActivity()
        enableEdgeToEdge()
        requestNotificationPermission()
        listenForEffects()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                ProfileScreen(
                    state = state,
                    onBackClick = viewmodel::onBackRequest,
                    onLogoutRequest = viewmodel::onLogoutRequest,
                    onLogoutConfirm = viewmodel::onLogoutConfirm,
                    onLogoutCancel = viewmodel::onLogoutCancel
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
                        ProfileNavigation.ToTitle -> finish()
                        ProfileNavigation.ToLogin -> {
                            navigateTo(LoginActivity::class.java, finish = true) {
                                flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            }
                        }
                    }
                }
            }
        }
    }
}
