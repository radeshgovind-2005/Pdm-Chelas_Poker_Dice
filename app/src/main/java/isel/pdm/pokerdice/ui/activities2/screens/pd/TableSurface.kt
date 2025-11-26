package isel.pdm.pokerdice.ui.activities.screens.pd

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TableSurface(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    bottomStart = 244.dp,
                    bottomEnd = 244.dp
                )
            )
            .drawWithCache {
                onDrawBehind {
                    drawRoundRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF3B0000), // Bright center
                                Color(0xFF8B0000), // Main color
                                Color(0xFF6B0000)  // Dark edges
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