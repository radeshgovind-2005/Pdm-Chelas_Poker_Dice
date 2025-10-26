package isel.pdm.chelaspokerdice.screens.lobby

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.screens.game.GameActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class LobbyActivity: ActivityNavigator() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LobbyScreen(
                    onNavigateBack = { navigate(LobbyNavigation.ToHome) },
                    onNavigateGame = { navigate(LobbyNavigation.ToGame) },
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: LobbyNavigation) {
        when (nav) {
            LobbyNavigation.ToGame -> navigationToScreen(GameActivity::class.java, Anim.Backwards)
            LobbyNavigation.ToHome -> navigationToScreen(MainActivity::class.java, Anim.Backwards)
        }
    }
}