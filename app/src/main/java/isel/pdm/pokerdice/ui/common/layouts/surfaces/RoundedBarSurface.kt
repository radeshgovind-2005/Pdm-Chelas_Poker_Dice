package isel.pdm.pokerdice.ui.common.layouts.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RoundedBarSurface(
    modifier: Modifier = Modifier,
    topbar: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
    Surface(
        modifier = modifier
            .height(64.dp)
            .shadow(elevation = 8.dp, shape = shape)
            .clip(shape),
        color = Color.Transparent,
        contentColor = Color.White
    ){
        Column (Modifier.fillMaxSize()){
            topbar()
        }
    }
}