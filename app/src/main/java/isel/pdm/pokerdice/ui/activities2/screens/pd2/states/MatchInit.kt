package isel.pdm.pokerdice.ui.activities.screens.pd2.states

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
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.theme.DarkWhite

@Composable
fun AnimatedPlayersRow(
    players: List<String> = listOf("Player6", "Player5", "Player4", "Player3", "Player2", "Player1"),
    modifier: Modifier = Modifier.fillMaxSize()
) {
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationStarted = true
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 150.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left group (first 3 players)
            players.take(3).forEachIndexed { index, player ->
                val offsetX by animateDpAsState(
                    targetValue = if (animationStarted) 0.dp else (-500 - index * 50).dp,
                    animationSpec = tween(durationMillis = 1000, delayMillis = index * 150),
                    label = "player_left_$index"
                )

                PlayerIcon(
                    playerName = player,
                    modifier = Modifier.offset(x = offsetX)
                )

                if (index < 2) {
                    Box(modifier = Modifier.size(16.dp))
                }
            }

            Box(modifier = Modifier.size(32.dp))

            // Right group (last 3 players)
            players.drop(3).take(3).forEachIndexed { index, player ->
                val offsetX by animateDpAsState(
                    targetValue = if (animationStarted) 0.dp else (500 + index * 50).dp,
                    animationSpec = tween(durationMillis = 1000, delayMillis = index * 150),
                    label = "player_right_$index"
                )

                PlayerIcon(
                    playerName = player,
                    modifier = Modifier.offset(x = offsetX)
                )

                if (index < 2) {
                    Box(modifier = Modifier.size(16.dp))
                }
            }
        }

    }
}

@Composable
fun PlayerIcon(
    playerName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.size(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SimpleIcon(MyIcon.Player, tint = DarkWhite)
            Spacer(modifier = Modifier.height(6.dp))
            Text(playerName, color = DarkWhite)
        }
    }
}