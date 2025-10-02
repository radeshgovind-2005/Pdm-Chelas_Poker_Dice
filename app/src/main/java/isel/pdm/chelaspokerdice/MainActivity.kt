package isel.pdm.chelaspokerdice

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.ui.navigation.TitleNavigation
import isel.pdm.chelaspokerdice.screens.about.AboutActivity
import isel.pdm.chelaspokerdice.screens.lobbies.LobbiesActivity
import isel.pdm.chelaspokerdice.screens.playerprofile.PlayerProfileActivity
import isel.pdm.chelaspokerdice.screens.title.TitleScreen
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class MainActivity : ActivityNavigator() {

    private val tag = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                TitleScreen(
                    onNavigateToAbout = { navigate(TitleNavigation.ToAbout) },
                    onNavigateToPlayerProfile = { navigate(TitleNavigation.ToPlayerProfile) },
                    onNavigateToLobbies = { navigate(TitleNavigation.ToLobbies) },
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: TitleNavigation) {
         when (nav) {
            TitleNavigation.ToAbout -> navigation(AboutActivity::class.java, Anim.Forward)
            TitleNavigation.ToLobbies ->navigation(LobbiesActivity::class.java, Anim.Forward)
            TitleNavigation.ToPlayerProfile ->navigation(PlayerProfileActivity::class.java, Anim.Forward)
        }
    }
}


