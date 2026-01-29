package isel.pdm.pokerdice.ui.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.ui.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyNavigation
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyState
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyViewModel

class LobbyActivity: BaseActivity<LobbyState, LobbyNavigation, LobbyViewModel>() {

    override val viewModel: LobbyViewModel by viewModels {
        val app = application as HostApp
        LobbyViewModel.getFactory(app.container.lobbyUseCase)
    }

    @Composable
    override fun ScreenContent(state: LobbyState) {
        LobbyScreen(
            state = state,
            onBackClick = viewModel::onBackRequest,
            onJoinRequest = viewModel::onJoinRequest,
            onStartMatch = viewModel::onStartRequest
        )
    }

    override fun handleEffect(effect: LobbyNavigation) {
        when (effect) {
            LobbyNavigation.ToBrowse -> navigateTo(BrowseActivity::class.java)
            is LobbyNavigation.ToMatch -> navigateTo(MatchActivity::class.java) {
                putExtra("MATCH_ID", effect.matchId)
            }
        }
    }

}
