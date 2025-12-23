package isel.pdm.pokerdice.ui.common.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PokerColorScheme = darkColorScheme(
    primary = PokerWhite,
    onPrimary = PokerBlack,
    secondary = PokerDarkWhite,
    tertiary = SoberVariant,
    background = PokerRedDeep,
    onBackground = PokerWhite,
    surface = PokerBlack,
    onSurface = PokerWhite,
)

@Composable
fun PokerdiceTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PokerColorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}