package isel.pdm.pokerdice.ui.activities.screens.pd2.table

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.theme.DarkChocoBrown
import isel.pdm.pokerdice.ui.theme.PureBlack
import isel.pdm.pokerdice.ui.theme.RedishBrown

val TableShape = RoundedCornerShape(
    bottomStart = 250.dp,
    bottomEnd = 250.dp
)
@Composable
fun Table(height: Dp, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(TableShape)
            .drawWithCache {
                onDrawBehind {
                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(DarkChocoBrown, RedishBrown, PureBlack)
                        )
                    )
                }
            }
            .padding(6.dp)
    ){
        content()
    }
}