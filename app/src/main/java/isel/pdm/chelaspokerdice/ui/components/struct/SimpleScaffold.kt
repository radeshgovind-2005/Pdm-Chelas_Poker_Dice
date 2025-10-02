package isel.pdm.chelaspokerdice.ui.components.struct

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleScaffold(
    topbar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    ) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { topbar() },
        floatingActionButton = { floatingActionButton() }
    ) { innerPadding ->
        content(innerPadding)
    }
}