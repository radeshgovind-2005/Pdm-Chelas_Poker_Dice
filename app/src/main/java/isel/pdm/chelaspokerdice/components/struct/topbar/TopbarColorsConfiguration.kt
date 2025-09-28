package isel.pdm.chelaspokerdice.components.struct.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
fun TopbarColorsConfiguration()  =
    TopAppBarColors(
        Color.Black,
        Color.Blue,
        Color.Red,
        Color.White,
        actionIconContentColor = Color.Black,
    )
