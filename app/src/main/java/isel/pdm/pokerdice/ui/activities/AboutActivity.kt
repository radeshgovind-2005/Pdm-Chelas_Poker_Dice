package isel.pdm.pokerdice.ui.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutState
import isel.pdm.pokerdice.ui.viewmodels.about.AboutViewModel

class AboutActivity: BaseActivity<AboutState, AboutNavigation, AboutViewModel>() {

    override val viewModel: AboutViewModel by viewModels { AboutViewModel.getFactory() }

    @Composable
    override fun ScreenContent(state: AboutState) {
        AboutScreen(
            state = state,
            onBackClick = viewModel::onBackRequest,
            onMailClick = viewModel::onMailRequest,
            onWebRequest = viewModel::onWebRequest
        )
    }

    override fun handleEffect(effect: AboutNavigation) {
        when (effect) {
            AboutNavigation.ToTitle -> finish()
            is AboutNavigation.ToMail -> navigateToMail(effect.sendTo, effect.subject)
            is AboutNavigation.ToWeb -> navigateToWeb(effect.uri)
        }
    }
}
