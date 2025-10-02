package isel.pdm.chelaspokerdice.screens.about

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.ui.navigation.AboutNavigation
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class AboutActivity : ActivityNavigator() {

    private val tag = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                AboutScreen(
                    onNavigateToTitleScreen = { navigate(AboutNavigation.ToTitleScreen)},
                    onNavigateToGameRules = { uri -> navigate(AboutNavigation.ToUri(uri)) },
                    onNavigateToMail = { emails, subject -> navigate(AboutNavigation.ToMail(emails,subject))}
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: AboutNavigation) {
        when (nav) {
            AboutNavigation.ToTitleScreen -> finish()
            is AboutNavigation.ToUri -> navigationToWeb(nav.uri)
            is AboutNavigation.ToMail -> navigationToMail(nav.emails, nav.subject)
        }
    }
}