package isel.pdm.pokerdice.ui.components.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MyIcon(val value: ImageVector){
    data object Back: MyIcon(Icons.AutoMirrored.Filled.ArrowBack)
}