package isel.pdm.pokerdice.ui.activities.screens.pd2.table

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.theme.DarkBurgundy
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import isel.pdm.pokerdice.ui.theme.PokerDarkRed

private val RoundShapeS = RoundedCornerShape(
    bottomStart = 244.dp,
    bottomEnd = 244.dp
)

@Composable
fun PokerTableSurface(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundShapeS)
            .drawWithCache {
                onDrawBehind {
                    drawRoundRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                DarkBurgundy, PokerDarkRed, MediumDarkRed
                            ),
                            center = Offset(size.width / 2, size.height / 2),
                            radius = size.width * 1.5f
                        )
                    )
                }
            },
        contentAlignment = Alignment.Center
    ){
        content()
    }
}