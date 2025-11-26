package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.AuthLog
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.TitleLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import kotlin.getValue

class TitleActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TitleLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        setContent {
            PokerDiceTheme {
                TitleScreen(
                    navToPlayerProfile = {navigate((Navigation.OnTitleScreen.ToPlayerProfile))},
                    navToAbout = { navigate(Navigation.OnTitleScreen.ToAbout)},
                    navToLobbies = {navigate(Navigation.OnTitleScreen.ToLobbies)},
                    user= null
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        TitleLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onDestroy() {
        super.onDestroy()
        TitleLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onResume() {
        super.onResume()
        TitleLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnTitleScreen) {
        TitleLog.logNavigation(nav)
        toScreen(nav.dest)
    }
}