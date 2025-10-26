package isel.pdm.chelaspokerdice.screens.lobby

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.HostApplication
import isel.pdm.chelaspokerdice.MainActivity
import isel.pdm.chelaspokerdice.screens.game.GameActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme
import isel.pdm.chelaspokerdice.vm.LobbyViewModel
import kotlin.getValue

class LobbyActivity: ActivityNavigator() {

    private val lobbyVm: LobbyViewModel by lazy {
        (application as HostApplication).lobbyViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LobbyScreen(
                    onNavigateBack = { navigate(LobbyNavigation.ToHome) },
                    onNavigateGame = { navigate(LobbyNavigation.ToGame) },
                    lobbyViewModel = lobbyVm
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