package isel.pdm.chelaspokerdice.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun MenuIcon(tint: Color = Color.White, onClick: () -> Unit = {}) {
    TopbarIcon(
        Icons.Rounded.Menu,"Menu", tint
    ){ onClick() }
}