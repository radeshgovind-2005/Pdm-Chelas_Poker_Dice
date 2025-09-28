package isel.pdm.chelaspokerdice.activities.lobbies

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.navigation.LobbiesNavigation
import isel.pdm.chelaspokerdice.navigation.PlayerProfileNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class LobbiesActivity : ActivityNavigator() {

    private val tag = this::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LobbiesScreen(
                    onNavigateToTitleScreen = { navigate(LobbiesNavigation.ToTitleScreen)}
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: LobbiesNavigation) {
        when (nav) {
            LobbiesNavigation.ToTitleScreen -> navigation(MainActivity::class.java, Anim.Backwards)
        }
    }
}