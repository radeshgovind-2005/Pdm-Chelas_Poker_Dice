package isel.pdm.chelaspokerdice.components.background

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import isel.pdm.chelaspokerdice.R

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(R.drawable.simple_background),
        contentDescription = "Logo Chelas Poker Dice",
        //modifier = Modifier.fillMaxHeight(), // Perguntar ao stor
        contentScale = ContentScale.Crop
    )
}