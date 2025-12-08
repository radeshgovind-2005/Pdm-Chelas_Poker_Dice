package isel.pdm.pokerdice.ui.animations.courtain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.pokerdice.ui.animations.transitions.VerticalTransition
import isel.pdm.pokerdice.ui.theme.curtainBrush

@Composable
fun Curtain(vFraction: Float, content: @Composable () -> Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(vFraction)
            .background(brush = curtainBrush)
    ){
        content()
    }
}
@Composable
fun CloseCurtain(
    isCurtainClosed: Boolean,
    onComplete: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    VerticalTransition(isCurtainClosed, onComplete) { yProgress ->
        Curtain(yProgress,content)
    }
}

@Composable
fun OpenCurtain(duration: Int = 3500, content: @Composable () -> Unit = {}) {
    VerticalTransition(duration=duration) { yProgress ->
        Curtain(1f - yProgress,content)
    }
}