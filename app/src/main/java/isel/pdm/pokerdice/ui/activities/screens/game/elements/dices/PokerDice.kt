package isel.pdm.pokerdice.ui.activities.screens.game.elements.dices

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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

private val SHADOW_COLOR = Color(0x40000000)

@Composable
fun PokerDice(isSelected: Boolean, onClick: () -> Unit, face:  String? = null,isTurn: Boolean = false){
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