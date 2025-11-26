package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import isel.pdm.pokerdice.MainLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.auth.AuthScreen
import isel.pdm.pokerdice.ui.activities.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.navigation.Navigation

class MainActivity : MyActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        setContent {
            SessionVerification(
                authenticatedScreen = { user ->
                    TitleScreen(
                        navToPlayerProfile = {navOnTitle(Navigation.OnTitleScreen.ToPlayerProfile)},
                        navToAbout = { navOnTitle(Navigation.OnTitleScreen.ToAbout)},
                        navToLobbies = {navOnTitle(Navigation.OnTitleScreen.ToLobbies)},
                    )
                },
                nonAuthenticatedScreen = {
                    AuthScreen(
                        navToTitle = {navOnAuth(Navigation.OnAuth.ToTitle) },
                        authViewModel = authViewModel
                    )
                }
            )
        }
    }

    override fun onPause() {
        super.onPause()
        MainLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onResume() {
        super.onResume()
        MainLog.logLifeCycle(getCurrentMethodName())
    }


    override fun onDestroy(){
        super.onDestroy()
        MainLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navOnAuth(nav: Navigation.OnAuth) {
        MainLog.logNavigation(nav)
        toScreen(nav.dest)
        finish()
    }

    private fun navOnTitle(nav: Navigation.OnTitleScreen) {
        MainLog.logNavigation(nav)
        toScreen(nav.dest)
    }
}