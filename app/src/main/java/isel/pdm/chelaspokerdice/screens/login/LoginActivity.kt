package isel.pdm.chelaspokerdice.screens.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator.Anim
import isel.pdm.chelaspokerdice.ui.navigation.LobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class LoginActivity: ActivityNavigator() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LoginScreen(
                    onNavigateToGame = { navigate(LobbyNavigation.ToGame) },
                    onNavigateToHome = { navigate(LobbyNavigation.ToHome) },
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: LobbyNavigation) {
        when (nav) {
            LobbyNavigation.ToGame -> finish()
            LobbyNavigation.ToHome -> navigationToScreen(MainActivity::class.java, Anim.Forward)
        }
    }
}