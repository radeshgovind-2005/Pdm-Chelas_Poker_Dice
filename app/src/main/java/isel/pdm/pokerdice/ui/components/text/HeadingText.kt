package isel.pdm.pokerdice.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

enum class HeadingLevel { H1, H2, H3 }

@Composable
fun HeadingText(
    text: String,
    level: HeadingLevel = HeadingLevel.H1,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    maxLines: Int = Int.MAX_VALUE
) {
    val style = when (level) {
        HeadingLevel.H1 -> MaterialTheme.typography.headlineLarge
        HeadingLevel.H2 -> MaterialTheme.typography.headlineMedium
        HeadingLevel.H3 -> MaterialTheme.typography.headlineSmall
    }
    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color,
        maxLines = maxLines
    )

}