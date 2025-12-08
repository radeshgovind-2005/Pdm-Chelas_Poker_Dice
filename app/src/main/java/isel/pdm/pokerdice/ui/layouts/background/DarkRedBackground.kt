package isel.pdm.pokerdice.ui.layouts.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.pokerdice.ui.theme.backgroundBrush

@Composable
fun DarkRedBackground(content: @Composable () -> Unit) {
    Box(
        modifier=Modifier
            .fillMaxSize()
            .background(brush = backgroundBrush)
    ) {
        content()
    }
}