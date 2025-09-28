package isel.pdm.chelaspokerdice.activities.about

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.navigation.AboutNavigation
import isel.pdm.chelaspokerdice.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.navigation.PlayerProfileNavigation
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
                    onNavigateToGameRules = { uri -> navigate(AboutNavigation.ToUri(uri)) }
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: AboutNavigation) {
        when (nav) {
            AboutNavigation.ToTitleScreen -> navigation(MainActivity::class.java, Anim.Backwards)
            is AboutNavigation.ToUri -> navigation(nav.uri)
        }
    }
}