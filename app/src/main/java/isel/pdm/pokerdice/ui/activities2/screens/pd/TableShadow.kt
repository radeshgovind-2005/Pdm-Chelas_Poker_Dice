package isel.pdm.pokerdice.ui.activities.screens.pd

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TableShadow() {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.96f)
            .height(295.dp)
            .clip(
                RoundedCornerShape(
                    bottomStart = 250.dp,
                    bottomEnd = 250.dp
                )
            )
            .background(Color(0x40000000))
    )
}