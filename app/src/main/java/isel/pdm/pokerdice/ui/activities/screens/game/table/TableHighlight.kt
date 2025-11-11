package isel.pdm.pokerdice.ui.activities.screens.game.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.theme.LightWhite
import isel.pdm.pokerdice.ui.theme.SubtleWhite
import isel.pdm.pokerdice.ui.theme.TransparentWhite

private val RoundShape = RoundedCornerShape(
    bottomStart = 240.dp,
    bottomEnd = 240.dp
)

@Composable
fun TableHighlight(width: Float = 1f,height: Dp){
    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height)
            .clip(RoundShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LightWhite, SubtleWhite, TransparentWhite)
                )
            )
    )

}