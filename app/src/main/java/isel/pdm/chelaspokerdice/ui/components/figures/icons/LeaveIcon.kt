package isel.pdm.chelaspokerdice.ui.components.figures.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LeaveIcon(tint: Color = Color.White, onClick: () -> Unit = {}) {
    TopbarIcon(
        Icons.Default.Close,"Leave", tint
    ){ onClick() }
}