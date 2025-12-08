package isel.pdm.pokerdice.ui.layouts.screens

import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.components.icons.BackIcon

@Composable
fun DefaultBackScreen(
    title: String,
    onClick: () -> Unit,
    fab: @Composable () -> Unit = {},
    content: @Composable ()->Unit
) {
    SimpleScreen(
        title=title,
        navigationIcon={BackIcon(onClick)},
        fab=fab
    ){
        content()
    }
}