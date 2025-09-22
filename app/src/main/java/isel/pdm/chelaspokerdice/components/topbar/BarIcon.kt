package isel.pdm.chelaspokerdice.components.topbar

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// Estamos a forçar um icone para que o titulo esteja centrado
@Composable
fun BarIcon(icon: ImageVector, color: Color = Color.Transparent) {
    IconButton(onClick = { }) {
        Icon(
            imageVector = icon, // conselho do IDE
            contentDescription = "Home",
            tint = color,
            modifier = Modifier.size(35.dp)
        )
    }
}