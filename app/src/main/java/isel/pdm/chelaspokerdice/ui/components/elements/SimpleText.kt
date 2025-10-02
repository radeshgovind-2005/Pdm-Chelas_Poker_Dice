package isel.pdm.chelaspokerdice.ui.components.elements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun SimpleText(text: String){
    Text(text = text, fontSize = 16.sp)
}