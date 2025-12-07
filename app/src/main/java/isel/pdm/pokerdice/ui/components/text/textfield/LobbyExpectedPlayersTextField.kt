package isel.pdm.pokerdice.ui.components.text.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.viewmodels.create.CreateState

@Composable
fun LobbyExpectedPlayersTextField(
    state: CreateState,
    onExpectedPlayersChange: (Int) -> Unit
) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.create_outfield_players) }
    OutlinedTextField(
        value = state.expectedPlayer?.toString() ?: "",
        onValueChange = { input ->
            if (input.all { it.isDigit() } && input.isNotEmpty()) {
                onExpectedPlayersChange(input.toInt())
            }
        },
        label = { Text(stringResource(titleResId)) },
        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = state.expectedPlayerError != null,
        supportingText = {
            if (state.expectedPlayerError != null) {
                Text(text = state.expectedPlayerError)
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}