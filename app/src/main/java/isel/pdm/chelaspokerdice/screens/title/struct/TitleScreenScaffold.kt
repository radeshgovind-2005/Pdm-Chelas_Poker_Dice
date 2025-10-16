package isel.pdm.chelaspokerdice.screens.title.struct

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.ui.components.figures.icons.MenuIcon
import isel.pdm.chelaspokerdice.ui.components.struct.SimpleScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.topbar.TopbarColorsConfiguration
import isel.pdm.chelaspokerdice.ui.components.struct.topbar.TopbarTitle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleScreenScaffold(modifier: Modifier, onClickMenu: () -> Unit, content: @Composable (PaddingValues) -> Unit) {

    val topbar = @Composable {
        TopAppBar(
            title = { TopbarTitle(stringResource(R.string.game_name)) },
            navigationIcon = { MenuIcon { onClickMenu() } },
            actions = { MenuIcon(Color.Transparent) },
            colors = TopbarColorsConfiguration()
        )
    }

    SimpleScaffold(
        modifier,
        topbar = topbar
    ) { innerPadding ->
        content(innerPadding)
    }

}