package isel.pdm.chelaspokerdice.ui.components.struct

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleScaffold(
    modifier: Modifier,
    topbar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    fabPosition: FabPosition = FabPosition.End,
    content: @Composable (PaddingValues) -> Unit,
    ) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { topbar() },
        floatingActionButton = { floatingActionButton() },
        floatingActionButtonPosition = fabPosition
    ) { innerPadding ->
        content(innerPadding)
    }
}