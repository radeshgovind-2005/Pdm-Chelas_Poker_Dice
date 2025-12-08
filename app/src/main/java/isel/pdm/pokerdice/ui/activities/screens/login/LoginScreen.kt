package isel.pdm.pokerdice.ui.activities.screens.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.SimpleCard
import isel.pdm.pokerdice.ui.layouts.AdaptiveLayout
import isel.pdm.pokerdice.ui.layouts.screens.SimpleScreen
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState

@Composable
fun LoginScreen(
    state: LoginState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.login_title) }
    val gamenameResId by rememberSaveable { mutableIntStateOf(R.string.game_name) }
    val titleStr=stringResource(titleResId)
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

