package isel.pdm.chelaspokerdice.screens.lobbies.struct

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import isel.pdm.chelaspokerdice.components.struct.SimpleScaffold
import isel.pdm.chelaspokerdice.components.icons.AddIcon
import isel.pdm.chelaspokerdice.components.struct.topbar.TopbarTitle
import isel.pdm.chelaspokerdice.components.icons.BackIcon
import isel.pdm.chelaspokerdice.components.struct.topbar.TopbarColorsConfiguration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbiesScaffold(onClickMenu: () -> Unit,content: @Composable (PaddingValues) -> Unit) {
    val topbar = @Composable {
        TopAppBar(
            title = { TopbarTitle("Lobbies") },
            navigationIcon = { BackIcon{ onClickMenu() } },
            actions = { BackIcon(Color.Transparent) },
            colors = TopbarColorsConfiguration()
        )
    }
    SimpleScaffold(
        topbar = topbar,
        floatingActionButton = { AddIcon() }
    ) { innerPadding ->
        content(innerPadding)
    }
}