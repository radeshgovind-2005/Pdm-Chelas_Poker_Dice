package isel.pdm.chelaspokerdice.ui.components.contentDisplay

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.ui.components.images.BackgroundImage


@Composable
fun ContentRowDisplay(
    innerPadding: PaddingValues,
    composableCode: @Composable () -> Unit
) {
    BackgroundImage()
    Row(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ){
        composableCode()
    }
}