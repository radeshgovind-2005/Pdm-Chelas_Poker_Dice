package isel.pdm.pokerdice.ui.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.app.HostApp
import isel.pdm.pokerdice.ui.screens.login.LoginScreen
import isel.pdm.pokerdice.ui.viewmodels.login.LoginNavigation
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState
import isel.pdm.pokerdice.ui.viewmodels.login.LoginViewModel

class LoginActivity : BaseActivity<LoginState, LoginNavigation, LoginViewModel>() {

    override val viewModel: LoginViewModel by viewModels {
        val app = application as HostApp
        LoginViewModel.provideFactory(
            owner=this,
            usecase = app.container.authUseCase
        )
    }
    @Composable
    override fun ScreenContent(state: LoginState) {
        LoginScreen(
            state = state,
            onUsernameChange = viewModel::onUsernameChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginClick = viewModel::signIn
        )
    }

    override fun handleEffect(effect: LoginNavigation) {
        when (effect) {
            LoginNavigation.ToTitle -> navigateTo(TitleActivity::class.java)
        }
    }
}
