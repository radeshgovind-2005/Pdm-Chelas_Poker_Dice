package isel.pdm.pokerdice.ui.components.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R

@Composable
fun PokerLogo(verticalPadding: Dp=16.dp, heightPortion: Int = 5){
    val logoResId by rememberSaveable { mutableIntStateOf(R.drawable.logo) }
    val logoHeight = LocalConfiguration.current.screenHeightDp / heightPortion
    Box(Modifier.padding(vertical=verticalPadding)){
        Image(
            painter= painterResource(logoResId),
            contentDescription="Poker Logo",
            modifier = Modifier.height(logoHeight.dp)
        )
    }

}