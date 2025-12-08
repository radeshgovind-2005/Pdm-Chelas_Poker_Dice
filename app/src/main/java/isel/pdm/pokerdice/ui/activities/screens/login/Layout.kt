package isel.pdm.pokerdice.ui.activities.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.components.images.PokerLogo
import isel.pdm.pokerdice.ui.components.text.BoldTitle
import isel.pdm.pokerdice.ui.components.text.TitleSize
import isel.pdm.pokerdice.ui.components.text.textfield.PasswordTextField
import isel.pdm.pokerdice.ui.components.text.textfield.UsernameTextField
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState

@Composable
fun PortraitLoginScreen(
    gamenameResId: Int,
    state: LoginState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    titleStr: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BoldTitle(stringResource(gamenameResId), TitleSize.LARGE)
        PokerLogo(verticalPadding = 56.dp)
        UsernameTextField(state, onUsernameChange)
        PasswordTextField(state, onPasswordChange)
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = onLoginClick,
                    enabled = state.isLoginEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(titleStr)
                }
            }
        }
    }
}

@Composable
fun LandscapeLoginScreen(
    gamenameResId: Int,
    state: LoginState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    titleStr: String
) {
    Row {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BoldTitle(stringResource(gamenameResId), TitleSize.LARGE)
            PokerLogo(heightPortion = 3)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UsernameTextField(state, onUsernameChange)
            PasswordTextField(state, onPasswordChange)
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.Bottom
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = onLoginClick,
                        enabled = state.isLoginEnabled,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(titleStr)
                    }
                }
            }
        }
    }
}