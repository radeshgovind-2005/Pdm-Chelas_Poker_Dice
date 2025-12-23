package isel.pdm.pokerdice.ui.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.common.components.card.SimpleCard
import isel.pdm.pokerdice.ui.common.layouts.AdaptiveLayout
import isel.pdm.pokerdice.ui.common.layouts.screens.SimpleScreen
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState

@Composable
fun LoginScreen(
    state: LoginState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    val gamenameResId =R.string.game_name
    val titleStr=stringResource(R.string.login_title)
    SimpleScreen(
        title= titleStr,
        content = {
            SimpleCard{
                AdaptiveLayout(
                    landscape={
                        LandscapeLoginScreen(gamenameResId, state, onUsernameChange, onPasswordChange, onLoginClick, titleStr)
                    },
                    portrait={
                        PortraitLoginScreen(gamenameResId, state, onUsernameChange, onPasswordChange, onLoginClick, titleStr)
                    }
                )
            }
        }
    )
}

