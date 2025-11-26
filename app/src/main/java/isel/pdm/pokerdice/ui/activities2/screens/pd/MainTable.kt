package isel.pdm.pokerdice.ui.activities.screens.pd

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MainTable(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(275.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 250.dp,
                    bottomEnd = 250.dp
                )
            )
            .drawWithCache {
                onDrawBehind {
                    // Wooden border effect
                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF3A1900),
                                Color(0xFF5D1C00),
                                Color(0xFF000000)
                            )
                        )
                    )
                }
            }
            .padding(6.dp)
    ){
        content()
    }
}