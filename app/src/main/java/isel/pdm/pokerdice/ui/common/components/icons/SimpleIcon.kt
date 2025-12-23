package isel.pdm.pokerdice.ui.common.components.icons

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

const val ICON_SIZE = 35
@Composable
fun SimpleIcon(
    icon: MyIcon,
    horizontalPadding: Int = 0,
    modifier: Modifier = Modifier,
    size: Int = ICON_SIZE,
    tint: Color =MaterialTheme.colorScheme.secondary
){
    Icon(
        imageVector = icon.value,
        contentDescription = icon.toString(),
        tint = tint,
        modifier = modifier
            .size(size.dp)
            .padding(horizontalPadding.dp)
    )
}

