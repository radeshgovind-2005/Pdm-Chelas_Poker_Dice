package isel.pdm.pokerdice.ui.components.images

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.remember.RememberImage

const val BOTTOM_PADDING = 32
@Composable
fun PokerDiceLogo(size: Int = 200) {
    RememberImage(
        resId = R.drawable.logo,
        description = "Logo",
        modifier = Modifier.size(size.dp).padding(bottom = BOTTOM_PADDING.dp)
    )
}
