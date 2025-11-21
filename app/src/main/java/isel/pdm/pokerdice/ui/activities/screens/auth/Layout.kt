package isel.pdm.pokerdice.ui.activities.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.images.PokerDiceLogo
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.layout.OneFullRow
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel

@Composable
fun PortraitAuthScreen(
    pv: PaddingValues,
    navToTitle: () -> Unit = {},
    authViewModel: AuthViewModel
){
    OneFullColumn(pv){
        DefaultCard (hca = Alignment.CenterHorizontally){
            Space(); HeadingText(RememberString(R.string.game_name))
            Space(); PokerDiceLogo()
            Space(); LoginForm(
            {credentials ->
                authViewModel.login(credentials.username,credentials.password)
                if(authViewModel.state is AuthViewModel.State.LoggedIn)
                    navToTitle()
            }
        ); Spacer(Modifier.height(16.dp))
        }
    }
}
@Composable
fun LandscapeAuthScreen(
    pv: PaddingValues,
    navToTitle: () -> Unit = {},
    authViewModel: AuthViewModel
){
        OneFullRow(
            {
                OneFullColumn {
                    DefaultCard (hca = Alignment.CenterHorizontally) {
                        HeadingText(RememberString(R.string.game_name))
                        Space(); PokerDiceLogo(150)
                    }
                }
            },
            {
                DefaultCard (hca = Alignment.CenterHorizontally) {
                    OneFullColumn {
                        Box(Modifier.fillMaxSize().background(Color.Yellow))
                        LoginForm(
                            { credentials ->
                                authViewModel.login(credentials.username, credentials.password)
                                if (authViewModel.state is AuthViewModel.State.LoggedIn)
                                    navToTitle()
                            }
                        )
                    }
                }
            },
            paddingValues = pv
        )

}
@Composable
private fun Space(){Spacer(Modifier.height(32.dp))}
