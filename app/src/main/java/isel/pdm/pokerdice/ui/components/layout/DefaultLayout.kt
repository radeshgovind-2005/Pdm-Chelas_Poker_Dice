package isel.pdm.pokerdice.ui.components.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.pokerdice.ui.components.images.DefaultBackground

@Composable
fun DefaultLayout(
    modifier: Modifier = Modifier,
    topbar: @Composable () -> Unit = {},
    floatingBtn: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = topbar,
        floatingActionButton = floatingBtn
    ) { innerPadding ->
        DefaultBackground()
        content(innerPadding)
    }
}