package isel.pdm.pokerdice.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun PlainText(text: String){
    Text(text = text, fontSize = 16.sp)
}