package isel.pdm.pokerdice.ui.components.topbar

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.icons.ICON_SIZE
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleButtonIcon
import isel.pdm.pokerdice.ui.components.layout.FullRow
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.components.text.HeadingLevel
import isel.pdm.pokerdice.ui.components.text.HeadingText

private const val SHADOW_ELAVATION = 8
private const val TONAL_ELAVATION = 4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    title: String? = null,
    navIcon: MyIcon? = null,
    onClick: () -> Unit = {},
    iconSize: Dp = ICON_SIZE.dp
) {
    val topbarShape = MaterialTheme.shapes.large
    val tbTitle = title ?: RememberString(R.string.game_name)
    Surface(
        shape = topbarShape,
        shadowElevation = SHADOW_ELAVATION.dp,
        tonalElevation = TONAL_ELAVATION.dp
    ) {
        TopAppBar(
            title = { FullRow { HeadingText(tbTitle, HeadingLevel.H2) } },
            navigationIcon = { navIcon?.let { SimpleButtonIcon(navIcon, onClick) } },
            actions = { navIcon?.let { Spacer(Modifier.size(iconSize)) } },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}
