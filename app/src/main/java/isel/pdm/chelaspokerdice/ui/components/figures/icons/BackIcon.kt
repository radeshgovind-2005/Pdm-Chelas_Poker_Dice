package isel.pdm.chelaspokerdice.ui.components.figures.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BackIcon(tint: Color = Color.White, onClick: () -> Unit = {}) {
    TopbarIcon(
        Icons.AutoMirrored.Filled.ArrowBack,"Back", tint
    ){ onClick() }
}