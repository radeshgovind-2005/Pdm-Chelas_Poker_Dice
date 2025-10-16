package isel.pdm.chelaspokerdice.ui.components.figures.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.playerprofile.BACK_BUTTON

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopbarIcon(icon: ImageVector, description: String, color: Color, onClick: () -> Unit) {
    IconButton( onClick = { onClick() }, modifier = Modifier.testTag(BACK_BUTTON) ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = color,
            modifier = Modifier.size(35.dp)
        )
    }
}