package isel.pdm.chelaspokerdice.components.contentDisplay

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RowDivider(
    width: Float = 1f,
    alignment: Alignment = Alignment.Center,
    padding: Int = 0,
    composableCode: @Composable () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(width)
            .padding(padding.dp)
        ,
        contentAlignment = alignment
    ){
        composableCode()
    }
}