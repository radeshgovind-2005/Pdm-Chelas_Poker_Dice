package isel.pdm.pokerdice.ui.activities.screens.game.states

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.verticalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.text.HeadingText
import isel.pdm.pokerdice.ui.theme.DarkWhite
import kotlinx.coroutines.delay


val DarkBurgundy = Color(0xFF3B0000)
val DeepRed = Color(0xFF8B0000)
val Maroon = Color(0xFF800000)

@Composable
fun AnimatedCloseCurtain(
    isVisible: Boolean,
    onAnimationComplete: () -> Unit = {}
) {
    var dotsFinished by remember { mutableStateOf(false) }

    // Animação da cortina descendo
    val curtainOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else -1f,
        animationSpec = tween(durationMillis = 800),
        label = "curtain_animation"
    )

    val dotCount by animateIntAsState(
        targetValue = if (isVisible) 3 else 0,
        animationSpec = keyframes {
            durationMillis = 3001
            0 at 0
            1 at 1000
            2 at 2000
            3 at 3000
        },
        label = "dots_animation",
        finishedListener = {
            dotsFinished = true
            if (isVisible) onAnimationComplete()
        }
    )

    if (isVisible || curtainOffset > -1f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (curtainOffset * 1000).dp) // Converte o offset para dp
        ) {
            // Cortina principal com gradiente de cores
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1200.dp) // Altura extra para garantir cobertura total
                    .background(
                        brush = verticalGradient(
                            colors = listOf(
                                DarkBurgundy,
                                DeepRed,
                                Maroon,
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
                    .align(Alignment.TopCenter)
            )

            // Efeito de dobras na cortina (opcional)
            RepeatPattern(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1200.dp)
                    .align(Alignment.TopCenter)
            )
            OneFullColumn {
                val text = "Configurating" + ".".repeat(dotCount)
                HeadingText(text, color = DarkWhite)
            }
        }
    }
}

@Composable
private fun RepeatPattern(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        // Adiciona algumas faixas verticais escuras para simular dobras
        for (i in 0..8) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.03f)
                    .height(1200.dp)
                    .background(Color(0x66000000))
                    .align(Alignment.TopStart)
                    .offset(x = (i * 12).percentAsDp())
            )
        }
    }
}

// Extensão para converter percentual em dp
private fun Int.percentAsDp() = (this * 12).dp

@Composable
fun AnimatedCloseCurtain2(
    isVisible: Boolean,
    onAnimationComplete: () -> Unit = {}
) {
    var dotsFinished by remember { mutableStateOf(false) }

    // Animação da cortina descendo
    val curtainOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else -1f,
        animationSpec = tween(durationMillis = 800),
        label = "curtain_animation"
    )

    val dotCount by animateIntAsState(
        targetValue = if (isVisible) 3 else 0,
        animationSpec = keyframes {
            durationMillis = 3001
            0 at 0
            1 at 1000
            2 at 2000
            3 at 3000
        },
        label = "dots_animation",
        finishedListener = {
            dotsFinished = true
            if (isVisible) onAnimationComplete()
        }
    )

    if (isVisible || curtainOffset > -1f) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (curtainOffset * 1000).dp) // Converte o offset para dp
        ) {
            // Cortina principal com gradiente de cores
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1200.dp) // Altura extra para garantir cobertura total
                    .background(
                        brush = verticalGradient(
                            colors = listOf(
                                DarkBurgundy,
                                DeepRed,
                                Maroon,
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
                    .align(Alignment.TopCenter)
            )

            // Efeito de dobras na cortina (opcional)
            RepeatPattern(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1200.dp)
                    .align(Alignment.TopCenter)
            )
            OneFullColumn {
                val text = "Configurating" + ".".repeat(dotCount)
                HeadingText(text, color = DarkWhite)
            }
        }
    }
}