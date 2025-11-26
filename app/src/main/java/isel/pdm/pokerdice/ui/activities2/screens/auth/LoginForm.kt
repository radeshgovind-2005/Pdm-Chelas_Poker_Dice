package isel.pdm.pokerdice.ui.activities.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.values.isValidName
import isel.pdm.pokerdice.domain.values.isValidPassword
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.inputs.types.NameTextField
import isel.pdm.pokerdice.ui.components.inputs.types.PasswordTextField
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun LoginForm(onClick: (credentials: UserCredentials) -> Unit) {
    var username by rememberSaveable  { mutableStateOf("") }
    var password by rememberSaveable  { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var usernameTouched by remember { mutableStateOf(false) }
    var passwordTouched by remember { mutableStateOf(false) }

    LaunchedEffect(username, password, usernameTouched, passwordTouched) {

        if(usernameTouched)
            usernameError = username.isValidName()?.message

        if(passwordTouched)
            passwordError = password.isValidPassword()?.message
    }
    NameTextField(
        value = username,
        onValueChange = {username = it; usernameTouched = true},
        errorMessage = usernameError,
        label = RememberString(R.string.loginform_username)
    )
    PasswordTextField(
        value = password,
        onValueChange = {password = it; passwordTouched = true},
        errorMessage = passwordError
    )
    val isValid =
        (usernameError == null)
                && (passwordError == null)
                && (username.isNotBlank())
                && (password.isNotBlank())
    Row (
        modifier = Modifier.fillMaxWidth(0.9f),
        horizontalArrangement = Arrangement.Center
    ){
        ButtonText(
            text=stringResource(R.string.loginform_btn),
            enabled = isValid,
            onClick = {if(isValid)onClick(UserCredentials(username,password))}
        )
    }
}