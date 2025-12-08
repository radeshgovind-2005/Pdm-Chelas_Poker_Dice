package isel.pdm.pokerdice.ui.components.text.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
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
import isel.pdm.pokerdice.ui.viewmodels.create.CreateState

@Composable
fun LobbyNameTextField(
    state: CreateState,
    onNameChange: (String) -> Unit
) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.create_outfield_name) }
    OutlinedTextField(
        value = state.name,
        onValueChange = onNameChange,
        label = { Text(stringResource(titleResId)) },
        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
        isError = state.nameError != null,
        supportingText = {
            if (state.nameError != null) {
                Text(text = state.nameError)
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}