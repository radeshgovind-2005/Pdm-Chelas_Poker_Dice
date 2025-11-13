package isel.pdm.pokerdice.ui.components.animations

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun VerticalAnimation(
    isVisible: Boolean = true,
    onComplete: () -> Unit = {},
    duration: Int = 1800,
    content: @Composable (Float) -> Unit
) {
    if(isVisible) {
        var shouldAnimate by remember { mutableStateOf(false) }
        val yProgress by animateFloatAsState(
            targetValue = if (shouldAnimate) 1f else 0f,
            animationSpec = tween(durationMillis = duration),
            finishedListener = {onComplete()}
        )
        LaunchedEffect(Unit) {
            shouldAnimate = true
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            content(yProgress)
        }
    }
}