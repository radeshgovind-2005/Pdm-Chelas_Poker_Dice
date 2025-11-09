package isel.pdm.pokerdice.ui.components.inputs.types

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.values.isValidNumberOfRounds
import isel.pdm.pokerdice.ui.components.inputs.DefaultTextField
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun NumberOfRoundsTextField(
    expectedPlayers: String,
    value: String,
    onValueChange: (String) -> Unit,
    onValidate: (String?) -> Unit = {},
    errorMessage: String?,
    label: String = RememberString(R.string.input_field_nofrounds),
    space: Boolean = true
){
    DefaultTextField(
        value = value,
        onChange = {
            onValueChange(it);
            onValidate(
                it.toIntOrNull()
                    ?.isValidNumberOfRounds(
                        expectedPlayers
                            .toInt()
                    )?.let { it.message }
            )
        },
        label = label,
        error = errorMessage,
        keyboardType = KeyboardType.Number
    )
    if(space) Spacer(Modifier.height(16.dp))
}