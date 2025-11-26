package isel.pdm.pokerdice.ui.activities.screens.auth

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.images.PokerDiceLogo
import isel.pdm.pokerdice.ui.components.layout.AdaptiveLayoutContent
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    navToTitle: () -> Unit = {},
    authViewModel: AuthViewModel
) {
    CommonLayout(){ pv ->
        AdaptiveLayoutContent(
            landscape = {LandscapeAuthScreen(pv,navToTitle,authViewModel)},
            portrait = {PortraitAuthScreen(pv,navToTitle,authViewModel)}
        )
    }
}
@Composable
private fun CommonLayout(content: @Composable (PaddingValues) -> Unit){
    DefaultLayout(
        topbar = {
            DefaultTopBar(
                title= RememberString(R.string.auth_title),
            )
        }
    ){ innerPadding ->
        content(innerPadding)
    }
}
