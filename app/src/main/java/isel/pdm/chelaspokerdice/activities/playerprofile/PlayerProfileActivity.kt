package isel.pdm.chelaspokerdice.activities.playerprofile

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.navigation.PlayerProfileNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class PlayerProfileActivity : ActivityNavigator() {

    private val tag = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                PlayerProfileScreen(
                    onNavigateToTitleScreen = { navigate(PlayerProfileNavigation.ToTitleScreen) }
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: PlayerProfileNavigation) {
        when (nav) {
            PlayerProfileNavigation.ToTitleScreen -> navigation(MainActivity::class.java, Anim.Backwards)
        }
    }

}