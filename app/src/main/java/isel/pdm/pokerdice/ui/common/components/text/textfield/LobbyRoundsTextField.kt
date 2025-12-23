package isel.pdm.pokerdice.ui.common.components.text.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
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
fun LobbyRoundsTextField(
    state: CreateState,
    onMaxRoundsChange: (Int) -> Unit
) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.create_outfield_rounds) }
    OutlinedTextField(
        value = state.maxRounds?.toString() ?: "",
        onValueChange = { input ->
            if (input.all { it.isDigit() } && input.isNotEmpty()) {
                onMaxRoundsChange(input.toInt())
            }
        },
        label = { Text(stringResource(titleResId)) },
        leadingIcon = { Icon(Icons.Default.Refresh, contentDescription = null) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = state.maxRoundsError != null,
        supportingText = {
            if (state.maxRoundsError != null) {
                Text(text = state.maxRoundsError)
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}