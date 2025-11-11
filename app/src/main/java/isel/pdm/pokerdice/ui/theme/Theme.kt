package isel.pdm.pokerdice.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

private val ColorScheme = darkColorScheme(
    primary = PokerBlack,
    secondary = PokerWhite,
    tertiary = PokerDarkRed,
    surfaceVariant = PokerSuperBlack,
    background = PokerDiceBackground,
    onBackground = DarkWhite
)

@Composable
fun PokerDiceTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = remember { ColorScheme }
    val typography = remember { Typography }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}