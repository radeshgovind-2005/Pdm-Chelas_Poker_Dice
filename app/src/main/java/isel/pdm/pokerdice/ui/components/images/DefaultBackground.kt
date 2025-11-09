package isel.pdm.pokerdice.ui.components.images

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.remember.RememberImage

@Composable
fun DefaultBackground() {
    RememberImage(
        resId = R.drawable.app_background,
        description = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

