package isel.pdm.pokerdice.ui.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.ui.screens.match.MatchScreen
import isel.pdm.pokerdice.ui.viewmodels.match.MatchNavigation
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState
import isel.pdm.pokerdice.ui.viewmodels.match.MatchViewModel

class MatchActivity : BaseActivity<MatchState, MatchNavigation, MatchViewModel>() {

    override val viewModel: MatchViewModel by viewModels {
        val app = application as HostApp
        MatchViewModel.getFactory(app.container.matchUseCase)
    }

    @Composable
    override fun ScreenContent(state: MatchState) {
        MatchScreen(
            state = state,
            onStartRound = viewModel::onStartRound,
            onClickDice = viewModel::onClickDice,
            onClickPlay = viewModel::onClickPlay,
            onClickNext = viewModel::onClickNext
        )
    }

    override fun handleEffect(effect: MatchNavigation) {
        when (effect) {
            is MatchNavigation.ToLobby -> navigateTo(LobbyActivity::class.java) {
                putExtra("LOBBY_ID", effect.lobbyId)
            }
            MatchNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
        }
    }
}
