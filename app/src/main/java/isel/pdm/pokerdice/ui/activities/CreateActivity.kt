package isel.pdm.pokerdice.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.screens.create.CreateScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutState
import isel.pdm.pokerdice.ui.viewmodels.create.CreateNavigation
import isel.pdm.pokerdice.ui.viewmodels.create.CreateState
import isel.pdm.pokerdice.ui.viewmodels.create.CreateViewModel
import kotlinx.coroutines.launch

class CreateActivity: BaseActivity<CreateState, CreateNavigation, CreateViewModel>() {

    override val viewModel: CreateViewModel by viewModels {
        val app = application as HostApp
        CreateViewModel.provideFactory(
            owner=this,
            usecase =app.container.createUseCase
        )
    }

    @Composable
    override fun ScreenContent(state: CreateState) {
        CreateScreen(
            state = state,
            onBackRequest = viewModel::toBackRequest,
            onNameChange = viewModel::onNameChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onExpectedPlayersChange = viewModel::onExpectedPlayersChange,
            onMaxRoundsChange = viewModel::onMaxRoundsChange,
            onBalanceChange = viewModel::onBalanceChange,
            onAnteChange = viewModel::onAnteChange,
            onCreateRequest = viewModel::onCreateLobby,
            onTryAgain = viewModel::onTryAgain
        )
    }

    override fun handleEffect(effect: CreateNavigation) {
        when (effect) {
            CreateNavigation.ToBrowse -> navigateTo(BrowseActivity::class.java)
            is CreateNavigation.ToLobby -> navigateTo(LobbyActivity::class.java) {
                putExtra("LOBBY_ID", effect.lobbyId)
            }
        }
    }

}