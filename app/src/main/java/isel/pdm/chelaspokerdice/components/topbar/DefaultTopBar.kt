package isel.pdm.chelaspokerdice.components.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(titleName: String, icon: ImageVector) {
    TopAppBar(
        title = { DefaultTitle(titleName) },
        navigationIcon = { BarIcon(icon, Color.White) },
        colors = DefaultColors(),
        actions = { BarIcon(icon) }
    )
}