package isel.pdm.pokerdice.ui.activities.screens.pd2.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import isel.pdm.pokerdice.ui.theme.SemiTransparent


@Composable
fun PokerTableShadow(width: Float=1f, height: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height)
            .clip(TableShape)
            .background(SemiTransparent)
    )
}