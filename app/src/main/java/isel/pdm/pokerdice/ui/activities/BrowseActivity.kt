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
import isel.pdm.pokerdice.ui.screens.browse.BrowseLobbiesScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutState
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseNavigation
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseState
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseViewModel
import kotlinx.coroutines.launch

class BrowseActivity : BaseActivity<BrowseState, BrowseNavigation, BrowseViewModel>() {

    override val viewModel: BrowseViewModel by viewModels {
        val app = application as HostApp
        BrowseViewModel.getFactory(app.container.browseUseCase)
    }

    @Composable
    override fun ScreenContent(state: BrowseState) {
        BrowseLobbiesScreen(
            state = state,
            onBackClick = viewModel::onBackRequest,
            onQueryChange = viewModel::onQueryChange,
            onSearch = viewModel::onChangeExpand,
            onCreateLobby = viewModel::onCreateRequest,
            onLobbyClick = viewModel::onLobbyRequest
        )
    }

    override fun handleEffect(effect: BrowseNavigation) {
        when (effect) {
            BrowseNavigation.ToCreateLobby -> navigateTo(CreateActivity::class.java)
            is BrowseNavigation.ToLobby -> navigateTo(LobbyActivity::class.java) {
                putExtra("LOBBY_ID", effect.lobbyId)
            }
            BrowseNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
        }
    }
}