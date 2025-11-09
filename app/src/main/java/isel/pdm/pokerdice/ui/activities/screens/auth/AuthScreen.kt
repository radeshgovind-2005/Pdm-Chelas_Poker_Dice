package isel.pdm.pokerdice.ui.activities.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.card.DefaultCard
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.components.images.PokerDiceLogo
import isel.pdm.pokerdice.ui.components.inputs.DefaultTextField
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
}
@Composable
private fun Space(){Spacer(Modifier.height(32.dp))}

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
