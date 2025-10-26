package isel.pdm.chelaspokerdice.screens.lobbies

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.HostApplication
import isel.pdm.chelaspokerdice.screens.about.AboutActivity
import isel.pdm.chelaspokerdice.screens.createlobby.CreateLobbyActivity
import isel.pdm.chelaspokerdice.screens.lobby.LobbyActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbiesNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme
import isel.pdm.chelaspokerdice.vm.LobbyViewModel
import kotlin.getValue

class LobbiesActivity : ActivityNavigator() {

    private val lobbyVm: LobbyViewModel by lazy {
        (application as HostApplication).lobbyViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LobbiesScreen(
                    onNavigateToTitleScreen = { navigate(LobbiesNavigation.ToTitleScreen) },
                    onNavigateToCreateLobby = { navigate(LobbiesNavigation.ToCreateLobby) },
                    onNavigateToLobby = { navigate(LobbiesNavigation.ToLobby) },
                    lobbyViewModel = lobbyVm
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: LobbiesNavigation) {
        when (nav) {
            LobbiesNavigation.ToTitleScreen -> finish()
            LobbiesNavigation.ToCreateLobby -> navigationToScreen(CreateLobbyActivity::class.java, Anim.Forward)
            LobbiesNavigation.ToLobby -> navigationToScreen(LobbyActivity::class.java, Anim.Forward)
        }
    }
}