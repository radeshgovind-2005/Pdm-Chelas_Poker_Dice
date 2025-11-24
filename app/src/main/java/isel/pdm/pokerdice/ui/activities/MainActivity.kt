package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import isel.pdm.pokerdice.AuthLog
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.auth.AuthScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel

class MainActivity : NavActivity() {


    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.getFactory((application as HostApplication).authUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AuthLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        setContent {
            PokerDiceTheme {
                AuthScreen(
                    navToTitle = { navigate(Navigation.OnAuth.ToTitle) },
                    authViewModel = authViewModel
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        AuthLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onResume() {
        super.onResume()
        AuthLog.logLifeCycle(getCurrentMethodName())
    }


    override fun onDestroy(){
        super.onDestroy()
        AuthLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnAuth) {
        AuthLog.logNavigation(nav)
        when (nav) {
            Navigation.OnAuth.ToTitle -> {
                toScreen(TitleActivity::class.java, Anim.Forward)
                finish()
            }
        }
    }
}