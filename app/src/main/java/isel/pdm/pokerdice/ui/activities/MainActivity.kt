package isel.pdm.pokerdice.ui.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.ui.screens.main.MainScreen
import isel.pdm.pokerdice.ui.viewmodels.main.MainNavigation
import isel.pdm.pokerdice.ui.viewmodels.main.MainState
import isel.pdm.pokerdice.ui.viewmodels.main.MainViewModel

class MainActivity : BaseActivity<MainState, MainNavigation, MainViewModel>() {

    override val viewModel: MainViewModel by viewModels {
        val app = application as HostApp
        MainViewModel.getFactory(app.container.mainUseCase)
    }

    @Composable
    override fun ScreenContent(state: MainState) {
        MainScreen(state=state)
    }

    override fun handleEffect(effect: MainNavigation) {
        when (effect) {
            MainNavigation.ToLogin -> navigateTo(LoginActivity::class.java)
            MainNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
            is MainNavigation.ToLobby -> navigateTo(LobbyActivity::class.java) {
                putExtra("LOBBY_ID", effect.lobbyId)
            }
            is MainNavigation.ToMatch -> navigateTo(MatchActivity::class.java) {
                putExtra("MATCH_ID", effect.matchId)
            }
        }
    }
}
