package isel.pdm.chelaspokerdice.screens.game

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.screens.lobby.LobbyScreen
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class GameActivity: ActivityNavigator() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                GameScreen(
                    onNavigateToHome = { navigate(LobbyNavigation.ToHome) },
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: LobbyNavigation) {
        when (nav) {
            LobbyNavigation.ToGame -> finish()
            LobbyNavigation.ToHome -> navigationToScreen(MainActivity::class.java, Anim.Backwards)
        }
    }
}