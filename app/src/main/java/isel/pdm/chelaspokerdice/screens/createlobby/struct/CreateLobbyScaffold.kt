package isel.pdm.chelaspokerdice.screens.createlobby.struct

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.ui.components.elements.ButtonText
import isel.pdm.chelaspokerdice.ui.components.figures.icons.AddIcon
import isel.pdm.chelaspokerdice.ui.components.figures.icons.BackIcon
import isel.pdm.chelaspokerdice.ui.components.struct.SimpleScaffold
import isel.pdm.chelaspokerdice.ui.components.struct.topbar.TopbarColorsConfiguration
import isel.pdm.chelaspokerdice.ui.components.struct.topbar.TopbarTitle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLobbyScaffold(
    modifier: Modifier ,
    onClickMenu: () -> Unit,
    onClickCreate: () -> Unit = {},
    enabled: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
) {
    val topbar = @Composable {
        TopAppBar(
            title = { TopbarTitle(stringResource(R.string.create_lobby)) },
            navigationIcon = { BackIcon{ onClickMenu() } },
            actions = { Spacer(modifier = Modifier.width(48.dp) )},
            colors = TopbarColorsConfiguration()
        )
    }
    SimpleScaffold(
        modifier = modifier,
        topbar = topbar,
        floatingActionButton = {
            ButtonText(
                stringResource(R.string.create_lobby),
                modifier = Modifier.size(300.dp,50.dp),
                buttonColor = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                enabled = enabled
            ){ onClickCreate() } },
        fabPosition = FabPosition.Center
    ) { innerPadding ->
        content(innerPadding)
    }
}