package isel.pdm.chelaspokerdice.screens.playerprofile.struct

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.ui.components.figures.icons.BackIcon
import isel.pdm.chelaspokerdice.ui.components.struct.SimpleScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.topbar.TopbarColorsConfiguration
import isel.pdm.chelaspokerdice.ui.components.struct.topbar.TopbarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerProfileScaffold(onClickMenu: () -> Unit,content: @Composable (PaddingValues) -> Unit) {
    val topbar = @Composable {
        TopAppBar(
            title = { TopbarTitle(stringResource(R.string.player_profile)) },
            navigationIcon = { BackIcon{ onClickMenu() } },
            actions = { Spacer(modifier = Modifier.width(48.dp) )},
            colors = TopbarColorsConfiguration()
        )
    }
    SimpleScaffold(
        Modifier,
        topbar = topbar,
    ) { innerPadding ->
        content(innerPadding)
    }
}