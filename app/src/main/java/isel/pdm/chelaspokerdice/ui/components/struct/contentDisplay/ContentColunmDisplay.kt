package isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.ui.components.figures.BackgroundImage

@Composable
fun ContentColunmDisplay(
    innerPadding: PaddingValues,
    vArrangement: Arrangement.Vertical = Arrangement.Center,
    composableCode: @Composable () -> Unit
) {
    BackgroundImage()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = vArrangement
    ) {
        composableCode()
    }
}