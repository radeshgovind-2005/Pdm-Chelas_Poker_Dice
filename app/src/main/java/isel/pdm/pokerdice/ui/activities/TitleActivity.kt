package isel.pdm.pokerdice.ui.activities

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.viewmodels.title.TitleNavigation
import isel.pdm.pokerdice.ui.viewmodels.title.TitleState
import isel.pdm.pokerdice.ui.viewmodels.title.TitleViewModel

class TitleActivity : BaseActivity<TitleState, TitleNavigation, TitleViewModel>() {

    override val viewModel: TitleViewModel by viewModels { TitleViewModel.getFactory() }

    @Composable
    override fun ScreenContent(state: TitleState) {
        TitleScreen(
            state=state,
            onClickAbout=viewModel::onAboutClicked,
            onClickLobbies=viewModel::onLobbiesClicked,
            onClickProfile=viewModel::onProfileClicked,
        )
    }

    override fun handleEffect(effect: TitleNavigation) {
        when (effect) {
            TitleNavigation.ToAbout -> navigateTo(AboutActivity::class.java, false)
            TitleNavigation.ToLobbies -> navigateTo(BrowseActivity::class.java, false)
            TitleNavigation.ToProfile -> navigateTo(ProfileActivity::class.java, false)
        }
    }
}
