package isel.pdm.chelaspokerdice.ui.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.R

@Composable
fun PokerDiceLogo(modifier: Modifier = Modifier, size: Int = 200) {
    Image(
        painter = painterResource(R.drawable.iconlight),
        contentDescription = "Poker Dice Icon",
        modifier = Modifier
            .size(size.dp)
            .padding(bottom = 32.dp)
    )
}