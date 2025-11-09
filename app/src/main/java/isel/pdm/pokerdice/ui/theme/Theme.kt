package isel.pdm.pokerdice.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private val ColorScheme = darkColorScheme(
    primary = PokerBlack,
    secondary = PokerWhite,
    tertiary = PokerDarkRed,
    surfaceVariant = PureBlack
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