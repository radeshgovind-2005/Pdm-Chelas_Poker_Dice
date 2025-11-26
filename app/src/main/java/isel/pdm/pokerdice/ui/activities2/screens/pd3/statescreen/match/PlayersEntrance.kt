package isel.pdm.pokerdice.ui.activities.screens.pd3.statescreen.match

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.domain.Users
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.theme.DarkWhite

private const val PLAYER_SIZE_DP = 60
@Composable
fun PlayersEntrance(players: Users) {
    var animationStarted by remember { mutableStateOf(false) }
    val screenHeight = LocalConfiguration.current.screenHeightDp

    LaunchedEffect(Unit) {
        animationStarted = true
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (screenHeight/3).dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            players.forEachIndexed { idx, player ->
                val offsetX by animateDpAsState(
                    targetValue = if (animationStarted) 0.dp else (-500 - idx * 50).dp,
                    animationSpec = tween(durationMillis = 1000, delayMillis = idx * 150),
                )

                Player(
                    name = player.userCredentials.username,
                    modifier = Modifier.offset(x = offsetX)
                )

            }
        }
    }
}

@Composable
private fun Player(name: String,modifier: Modifier){
    Box(
        modifier = modifier.size(PLAYER_SIZE_DP.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleIcon(MyIcon.Player, tint = DarkWhite)
            Spacer(modifier = Modifier.height(6.dp))
            Text(name, color = DarkWhite)
        }
    }
}