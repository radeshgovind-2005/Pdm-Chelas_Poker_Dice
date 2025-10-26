package isel.pdm.chelaspokerdice.ui.components.elements

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MediumText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}