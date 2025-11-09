package isel.pdm.pokerdice.ui.components.inputs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun String.ShowError() = Text(
    text = this,
    color = MaterialTheme.colorScheme.inversePrimary,
    style = MaterialTheme.typography.bodySmall,
    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
)
