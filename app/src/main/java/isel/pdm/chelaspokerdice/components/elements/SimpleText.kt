package isel.pdm.chelaspokerdice.components.elements

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import isel.pdm.chelaspokerdice.activities.about.content.ABOUT_THIS_PROJECT

@Composable
fun SimpleText(text: String){
    Text(text = text, fontSize = 16.sp)
}