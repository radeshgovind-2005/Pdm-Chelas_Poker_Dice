package isel.pdm.pokerdice.ui.layouts.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import isel.pdm.pokerdice.ui.layouts.background.DarkRedBackground
import isel.pdm.pokerdice.ui.layouts.topbar.RounderTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleScreen(
    title: String,
    navigationIcon:@Composable () -> Unit={},
    fab: @Composable () -> Unit={},
    content: @Composable () -> Unit
) {
    DarkRedBackground {
        Scaffold(
            topBar = { RounderTopBar(title, navigationIcon) },
            floatingActionButton = {fab()},
            containerColor = Color.Transparent
        ) { paddingValues ->
            Box(
                Modifier.fillMaxSize().padding(paddingValues)) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun Prev(){
    SimpleScreen("Preview") { }
}