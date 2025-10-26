package isel.pdm.chelaspokerdice.ui.components.figures.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LogoutIcon(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = Modifier,
        containerColor = Color.Black
    ) {
        Icon(
            Icons.AutoMirrored.Filled.ExitToApp,
            contentDescription = "Log out",
            tint = Color.White,
            modifier = Modifier.size(35.dp)
        )
    }
}