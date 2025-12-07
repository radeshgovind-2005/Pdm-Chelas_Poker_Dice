package isel.pdm.pokerdice.ui.components.text.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState

@Composable
fun UsernameTextField(
    state: LoginState,
    onUsernameChange: (String) -> Unit,
) {
    val usernameResId by rememberSaveable { mutableIntStateOf(R.string.username_textfield) }
    OutlinedTextField(
        value = state.username,
        onValueChange = onUsernameChange,
        label = { Text(stringResource(usernameResId)) },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        isError = state.usernameError != null,
        supportingText = {
            if (state.usernameError != null) {
                Text(text = state.usernameError)
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}