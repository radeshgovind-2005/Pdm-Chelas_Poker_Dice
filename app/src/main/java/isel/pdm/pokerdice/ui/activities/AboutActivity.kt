package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import isel.pdm.pokerdice.AboutLog
import isel.pdm.pokerdice.getCurrentMethodName
import isel.pdm.pokerdice.ui.activities.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme

class AboutActivity : NavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AboutLog.logLifeCycle(getCurrentMethodName())
        enableEdgeToEdge()
        setContent {
            PokerDiceTheme {
               AboutScreen(
                   navBack = { navigate(Navigation.OnAbout.GoBack)},
                   navToWeb = { link -> navigate(Navigation.OnAbout.ToWeb(link))},
                   navToMail = { sendTo,subject -> navigate(Navigation.OnAbout.ToMail(sendTo,subject))},
               )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        AboutLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onDestroy() {
        super.onDestroy()
        AboutLog.logLifeCycle(getCurrentMethodName())
    }
    override fun onResume() {
        super.onResume()
        AboutLog.logLifeCycle(getCurrentMethodName())
    }

    private fun navigate(nav: Navigation.OnAbout) {
        AboutLog.logNavigation(nav)
        when (nav) {
            Navigation.OnAbout.GoBack -> finish()
            is Navigation.OnAbout.ToMail -> toMail(nav.sendTo, nav.subject)
            is Navigation.OnAbout.ToWeb -> toWeb(nav.link)
        }
    }
}