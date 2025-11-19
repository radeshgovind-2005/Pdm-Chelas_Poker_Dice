package isel.pdm.pokerdice.ui.activities.screens.game.effects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import isel.pdm.pokerdice.ui.components.animations.VerticalAnimation


val DarkBurgundy = Color(0xFF3B0000)
val DeepRed = Color(0xFF8B0000)
val Maroon = Color(0xFF800000)

@Composable
fun Curtain(vFraction: Float){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(vFraction)
            .background(
                brush = verticalGradient(
                    colors = listOf(DarkBurgundy, DeepRed, Maroon),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    )
}

@Composable
fun CloseCurtain(
    isCurtainClosed: Boolean,
    onComplete: () -> Unit,
) {
    VerticalAnimation(isCurtainClosed, onComplete) { yProgress ->
        Curtain(yProgress)
    }
}

@Composable
fun OpenCurtain() {
    VerticalAnimation { yProgress ->
        Curtain(1f - yProgress)
    }
}