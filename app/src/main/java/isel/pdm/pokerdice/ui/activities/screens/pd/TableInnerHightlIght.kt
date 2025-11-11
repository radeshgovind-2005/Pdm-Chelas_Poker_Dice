package isel.pdm.pokerdice.ui.activities.screens.pd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TableInnerHightlIght(){
    Box(
        modifier = Modifier
            .fillMaxWidth(0.88f)
            .height(290.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 240.dp,
                    bottomEnd = 240.dp
                )
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0x30FFFFFF),
                        Color(0x10FFFFFF),
                        Color(0x00FFFFFF)
                    )
                )
            )
    )
}