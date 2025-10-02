package isel.pdm.chelaspokerdice.ui.components.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddIcon() {
    FloatingActionButton(
        onClick = { },
        containerColor = Color.Green ) {
        Icon(
            Icons.Filled.Add,
            contentDescription = "Create Lobbies",
            tint = Color.White,
            modifier = Modifier.size(35.dp)
        )
    }
}