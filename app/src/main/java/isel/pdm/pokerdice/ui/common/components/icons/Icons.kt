package isel.pdm.pokerdice.ui.common.components.icons

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun BackIcon(onClick: () -> Unit){
    IconButton(onClick= onClick) {
        SimpleIcon(MyIcon.Back)
    }
}