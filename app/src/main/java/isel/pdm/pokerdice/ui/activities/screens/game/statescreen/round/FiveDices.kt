package isel.pdm.pokerdice.ui.activities.screens.game.statescreen.round

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
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn

private val SHADOW_COLOR = Color(0x40000000)

@Composable
fun FiveDices(faces: List<String>,selectedIndices: List<Int>, onSelect: (Int) -> Unit, isTurn: Boolean) {
    OneFullColumn {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            faces.forEachIndexed { idx, face ->
                Dice(
                    isSelected = selectedIndices.contains(idx),
                    onClick = {onSelect(idx)},
                    face = face,
                    isTurn = isTurn
                )
            }
        }
    }
}

@Composable
private fun Dice(isSelected: Boolean, onClick: () -> Unit, face:  String? = null,isTurn: Boolean = false){
    val diceSize = if (isSelected) 48.dp else 40.dp
    val borderWidth = if (isSelected) 3.dp else 0.dp
    val borderColor = if (isSelected) Color.Black else Color.Transparent
    val diceColor = if(isTurn) Color.White else Color.LightGray
    val faceColor = if(isTurn) listOf(Color.Black,Color.Red).random() else Color.DarkGray
    Box(
        modifier = Modifier
            .size(diceSize)
            .background(
                color = diceColor,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .drawWithCache {
                onDrawBehind {
                    drawRect(
                        color = SHADOW_COLOR,
                        topLeft = Offset(2f, 2f),
                        size = size.copy(width = size.width - 4f, height = size.height - 4f)
                    )
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(face ?: "?", color = faceColor)
    }
}