package isel.pdm.pokerdice.ui.common.components.text.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
fun LobbyDescriptionTextField(
    state: CreateState,
    onDescriptionChange: (String) -> Unit
) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.create_outfield_desc) }
    OutlinedTextField(
        value = state.description,
        onValueChange = onDescriptionChange,
        label = { Text(stringResource(titleResId)) },
        leadingIcon = { Icon(Icons.Default.Info, contentDescription = null) },
        isError = state.descriptionError != null,
        supportingText = {
            if (state.descriptionError != null) {
                Text(text = state.descriptionError)
            }
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}