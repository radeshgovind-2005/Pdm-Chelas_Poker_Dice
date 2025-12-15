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
import isel.pdm.pokerdice.ui.activities.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.viewmodels.title.TitleNavigation
import isel.pdm.pokerdice.ui.viewmodels.title.TitleViewModel

class TitleActivity : ComponentActivity() {

    val logger by lazy{ AppLog(this::class.java.simpleName) }
    val viewmodel: TitleViewModel by viewModels { TitleViewModel.getFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.lifeCycle("onCreate")
        enableEdgeToEdge()
        requestNotificationPermission()
        setContent {
            PokerdiceTheme {
                val state by viewmodel.state.collectAsState()
                ListenForEffects()
                TitleScreen(
                    state=state,
                    onClickAbout=viewmodel::onAboutClicked,
                    onClickLobbies=viewmodel::onLobbiesClicked,
                    onClickProfile=viewmodel::onProfileClicked,
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
                    TitleNavigation.ToAbout -> navigateTo(AboutActivity::class.java,false)
                    TitleNavigation.ToLobbies -> navigateTo(BrowseActivity::class.java,false)
                    TitleNavigation.ToProfile -> navigateTo(ProfileActivity::class.java,false)

                }
            }
        }
    }
}
