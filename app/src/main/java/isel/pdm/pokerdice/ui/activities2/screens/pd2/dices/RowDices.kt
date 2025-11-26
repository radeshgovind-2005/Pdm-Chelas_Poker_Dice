package isel.pdm.pokerdice.ui.activities.screens.pd2.dices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// In your dices package
@Composable
fun RowDices(
    faces: List<String>,
    selectedIndices: List<Int>,
    onDiceSelected: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) { index ->
            PokerDice(
                isSelected = selectedIndices.contains(index),
                onDiceClick = { onDiceSelected(index) }
            ) {
                Text(faces.getOrElse(index) { "?" }, color = Color.Black)
            }
        }
    }
}

@Composable
private fun PokerDice(
    isSelected: Boolean,
    onDiceClick: () -> Unit,
    face: @Composable () -> Unit
) {
    val diceSize = if (isSelected) 48.dp else 40.dp
    val borderWidth = if (isSelected) 3.dp else 0.dp
    val borderColor = if (isSelected) Color.Blue else Color.Transparent

    Box(
        modifier = Modifier
            .size(diceSize)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onDiceClick() }
            .drawWithCache {
                onDrawBehind {
                    drawRect(
                        color = Color(0x40000000),
                        topLeft = Offset(2f, 2f),
                        size = size.copy(width = size.width - 4f, height = size.height - 4f)
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        face()
    }
}