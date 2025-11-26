package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import isel.pdm.pokerdice.AuthLog
import isel.pdm.pokerdice.LogTag
import isel.pdm.pokerdice.MainLog
import isel.pdm.pokerdice.TitleLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.auth.AuthScreen
import isel.pdm.pokerdice.ui.activities.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel

class MainActivity : MyActivity() {

    var tag: LogTag = MainLog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tag.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        authViewModel.getCurrentUser()
        setContent {
            PokerDiceTheme {
                val state by authViewModel.state.collectAsState()
                when(state){
                    is AuthViewModel.State.LoggedIn -> {
                        tag = TitleLog
                        TitleScreen(
                            navToPlayerProfile = {navOnTitle((Navigation.OnTitleScreen.ToPlayerProfile))},
                            navToAbout = { navOnTitle(Navigation.OnTitleScreen.ToAbout)},
                            navToLobbies = {navOnTitle(Navigation.OnTitleScreen.ToLobbies)}
                        )
                    }
                    else -> {
                        tag = AuthLog
                        AuthScreen(
                            navToTitle = { navOnAuth(Navigation.OnAuth.ToTitle) },
                            authViewModel = authViewModel
                        )
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        tag.logLifeCycle(getCurrentMethodName())
    }
    override fun onResume() {
        super.onResume()
        tag.logLifeCycle(getCurrentMethodName())
    }


    override fun onDestroy(){
        super.onDestroy()
        tag.logLifeCycle(getCurrentMethodName())
    }

    private fun navOnAuth(nav: Navigation.OnAuth) {
        tag.logNavigation(nav)
        when (nav) {
            Navigation.OnAuth.ToTitle -> {
                toScreen(TitleActivity::class.java, Anim.Forward)
                finish()
            }
        }
    }

    private fun navOnTitle(nav: Navigation.OnTitleScreen) {
        tag.logNavigation(nav)
        when (nav) {
            Navigation.OnTitleScreen.ToLobbies -> toScreen(
                LobbiesActivity::class.java,
                Anim.Forward
            )
            Navigation.OnTitleScreen.ToAbout -> toScreen(AboutActivity::class.java, Anim.Forward)
            Navigation.OnTitleScreen.ToPlayerProfile -> toScreen(
                PlayerProfileActivity::class.java,
                Anim.Forward
            )
        }
    }
}