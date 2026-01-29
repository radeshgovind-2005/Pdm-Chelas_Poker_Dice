package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseActivity<S, E, VM : BaseViewModel<S, E>>: ComponentActivity() {
    val logger by lazy { AppLog(this::class.java.simpleName) }
    abstract val viewModel: VM
    @Composable
    abstract fun ScreenContent(state: S)
    abstract fun handleEffect(effect: E)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        enableEdgeToEdge()
        requestNotificationPermission()
        listenForEffects()
        setContent{
            PokerdiceTheme {
                val state by viewModel.state.collectAsState()
                ScreenContent(state)
            }
        }
    }
    override fun onStart() { super.onStart(); logger.lifeCycle("onStart") }
    override fun onResume() { super.onResume(); logger.lifeCycle("onResume") }
    override fun onPause() { super.onPause(); logger.lifeCycle("onPause") }
    override fun onStop() { super.onStop(); logger.lifeCycle("onStop") }
    override fun onRestart() { super.onRestart(); logger.lifeCycle("onRestart") }
    override fun onDestroy() { super.onDestroy(); logger.lifeCycle("onDestroy") }
    private fun listenForEffects() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                logger.i("Listening for Effects")
                viewModel.effects.collect { effect ->
                    logger.i("Effect collected -> ${effect!!::class.java.simpleName}")
                    handleEffect(effect)
                }
            }
        }
    }
}