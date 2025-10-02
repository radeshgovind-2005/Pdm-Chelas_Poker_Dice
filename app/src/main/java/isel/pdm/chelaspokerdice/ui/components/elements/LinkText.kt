package isel.pdm.chelaspokerdice.ui.components.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun LinkText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.Blue,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        textDecoration = TextDecoration.Underline
    )
}