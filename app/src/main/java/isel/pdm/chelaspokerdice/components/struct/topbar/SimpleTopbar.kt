package isel.pdm.chelaspokerdice.components.struct.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import isel.pdm.chelaspokerdice.components.icons.SimpleIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopbar(
    title: String,
    icon: SimpleIcon,
    onClickMenu: () -> Unit
): @Composable (() -> Unit) = {
    TopAppBar(
        title = { TopbarTitle(title) },
        navigationIcon = { icon.ShowIcon() { onClickMenu() } },
        actions = {icon.ShowIcon(tint = Color.Transparent) },
        colors = TopbarColorsConfiguration()
    )
}