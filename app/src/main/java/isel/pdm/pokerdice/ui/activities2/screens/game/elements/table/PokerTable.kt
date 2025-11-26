package isel.pdm.pokerdice.ui.activities.screens.game.elements.table

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.layout.ColumnTopCenter
import isel.pdm.pokerdice.ui.components.text.CursiveTitle
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.theme.DarkBurgundy
import isel.pdm.pokerdice.ui.theme.DarkChocoBrown
import isel.pdm.pokerdice.ui.theme.LightWhite
import isel.pdm.pokerdice.ui.theme.MediumDarkRed
import isel.pdm.pokerdice.ui.theme.PokerDarkRed
import isel.pdm.pokerdice.ui.theme.PureBlack
import isel.pdm.pokerdice.ui.theme.RedishBrown
import isel.pdm.pokerdice.ui.theme.SemiTransparent
import isel.pdm.pokerdice.ui.theme.SubtleWhite
import isel.pdm.pokerdice.ui.theme.TransparentWhite

private val TableShape = RoundedCornerShape(
    bottomStart = 250.dp,
    bottomEnd = 250.dp
)
private val RoundShape = RoundedCornerShape(
    bottomStart = 240.dp,
    bottomEnd = 240.dp
)
private val RoundShapeS = RoundedCornerShape(
    bottomStart = 244.dp,
    bottomEnd = 244.dp
)


@Composable
fun PokerTable(content: @Composable () -> Unit) {
    Shadow(0.96f,295.dp)
    Table(275.dp) {
        PokerSurface {
            ColumnTopCenter {
                CursiveTitle(RememberString(R.string.game_name))
                content()
            }
        }
    }
    Highlight(0.88f,290.dp)

}

@Composable
private fun Shadow(width: Float=1f, height: Dp){
    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height)
            .clip(TableShape)
            .background(SemiTransparent)
    )
}

@Composable
private fun Table(height: Dp, content: @Composable () -> Unit) {
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

@Composable
private fun Highlight(width: Float = 1f,height: Dp){
    Box(
        modifier = Modifier
            .fillMaxWidth(width)
            .height(height)
            .clip(RoundShape)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(LightWhite, SubtleWhite, TransparentWhite)
                )
            )
    )
}

@Composable
fun PokerSurface(content: @Composable () -> Unit) {
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