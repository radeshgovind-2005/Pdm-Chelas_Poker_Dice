package isel.pdm.pokerdice.ui.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

typealias ComposeContent = @Composable () -> Unit
@Composable
fun OneFullRow(vararg contents: ComposeContent, paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        contents.forEach { content ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(paddingValues)
                ,
                contentAlignment = Alignment.Center
            ){
                content()
            }
        }
    }
}