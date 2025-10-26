package isel.pdm.chelaspokerdice

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.screens.about.AboutActivity
import isel.pdm.chelaspokerdice.screens.lobbies.LobbiesActivity
import isel.pdm.chelaspokerdice.screens.playerprofile.PlayerProfileActivity
import isel.pdm.chelaspokerdice.screens.title.TitleScreen
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.TitleNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class MainActivity : ActivityNavigator() {

    private val tag = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(tag, "MainActivity.onCreate.")
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
            TitleNavigation.ToAbout -> navigationToScreen(AboutActivity::class.java, Anim.Forward)
            TitleNavigation.ToLobbies -> navigationToScreen(LobbiesActivity::class.java, Anim.Forward)
            TitleNavigation.ToPlayerProfile -> navigationToScreen(PlayerProfileActivity::class.java, Anim.Forward)
        }
    }
}



