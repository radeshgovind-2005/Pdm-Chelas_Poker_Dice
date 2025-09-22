package isel.pdm.chelaspokerdice.components.topbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultColors() = TopAppBarColors(
    Color.Black,
    Color.Blue,
    Color.Red,
    Color.White,
    Color.Black
)