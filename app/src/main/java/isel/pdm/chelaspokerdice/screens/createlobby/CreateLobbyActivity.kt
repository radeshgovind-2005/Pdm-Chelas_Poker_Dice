package isel.pdm.chelaspokerdice.screens.createlobby

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.HostApplication
import isel.pdm.chelaspokerdice.screens.lobby.LobbyActivity
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.CreateLobbyNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme
import isel.pdm.chelaspokerdice.vm.LobbyViewModel
import kotlin.getValue

class CreateLobbyActivity: ActivityNavigator() {

    private val lobbyVm: LobbyViewModel by lazy {
        (application as HostApplication).lobbyViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                CreateLobbyScreen(
                    onNavigateToLobbies = { navigate(CreateLobbyNavigation.ToLobbies) },
                    onNavigateToLobby = { navigate(CreateLobbyNavigation.ToLobby) },
                    lobbyViewModel = lobbyVm
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: CreateLobbyNavigation) {
        when (nav) {
            CreateLobbyNavigation.ToLobbies -> finish()
            CreateLobbyNavigation.ToLobby -> navigationToScreen(LobbyActivity::class.java, Anim.Forward)
        }
    }
}