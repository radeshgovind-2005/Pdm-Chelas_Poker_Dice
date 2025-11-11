package isel.pdm.pokerdice.ui.components.text

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CursiveTitle(text: String, size: Int = 20, topPadding: Int = 30, spacing: Int = 2) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = size.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.Cursive,
        modifier = Modifier.padding(top = topPadding.dp),
        letterSpacing = spacing.sp
    )
}