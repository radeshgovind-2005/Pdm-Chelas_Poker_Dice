package isel.pdm.pokerdice.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.ui.common.theme.PokerdiceTheme
import isel.pdm.pokerdice.ui.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.screens.profile.ProfileScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutState
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileNavigation
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileState
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileActivity : BaseActivity<ProfileState, ProfileNavigation, ProfileViewModel>() {


    override val viewModel: ProfileViewModel by viewModels {
        val app = application as HostApp
        ProfileViewModel.getFactory(app.container.profileUseCase)
    }

    @Composable
    override fun ScreenContent(state: ProfileState) {
        ProfileScreen(
            state = state,
            onBackClick = viewModel::onBackRequest,
            onLogoutRequest = viewModel::onLogoutRequest,
            onLogoutConfirm = viewModel::onLogoutConfirm,
            onLogoutCancel = viewModel::onLogoutCancel
        )
    }

    override fun handleEffect(effect: ProfileNavigation) {
        when (effect) {
            ProfileNavigation.ToTitle -> finish()
            ProfileNavigation.ToLogin -> {
                navigateTo(LoginActivity::class.java, finish = true) {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }
    }

}
