package isel.pdm.pokerdice.ui.components.text.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState

@Composable
fun PasswordTextField(
    state: LoginState,
    onPasswordChange: (String) -> Unit,
) {
    val passResId by rememberSaveable { mutableIntStateOf(R.string.password_textfield) }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    OutlinedTextField(
        value = state.pass,
        onValueChange = onPasswordChange,
        label = { Text(stringResource(passResId)) },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            val image = if (isPasswordVisible) Icons.Filled.FavoriteBorder else Icons.Filled.Favorite
            val description = if (isPasswordVisible) "Hide password" else "Show password"
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = state.passError != null,
        supportingText = {
            if (state.passError != null) {
                Text(text = state.passError)
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}