package isel.pdm.pokerdice.ui.components.layout

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
import androidx.compose.ui.unit.dp

@Composable
fun OneFullColumn(
    paddingValues: PaddingValues=PaddingValues(0.dp),
    vArrangment: Arrangement.Vertical =  Arrangement.Center,
    hAlignment: Alignment.Horizontal =  Alignment.CenterHorizontally,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues),
        verticalArrangement = vArrangment,
        horizontalAlignment = hAlignment,
    ) {
        content()
    }
}