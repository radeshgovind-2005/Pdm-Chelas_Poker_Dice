package isel.pdm.pokerdice.ui.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DefaultTextField(
    value: String,
    onChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    label: String,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        SimpleRoundedTextField(
            value = value,
            onValueChange = {onChange(it)},
            keyboardType = keyboardType,
            label = label,
            isError = error != null,
            visualTransformation = visualTransformation
        )
        error?.ShowError()
    }
}