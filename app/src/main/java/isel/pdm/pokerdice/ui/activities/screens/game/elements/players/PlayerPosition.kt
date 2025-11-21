package isel.pdm.pokerdice.ui.activities.screens.game.elements.players

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class PlayerPosition(
    val xOffset: (screenWidth: Int) -> Dp,
    val yOffset: (screenWidth: Int) -> Dp
)
const val MIN_PLAYERS = 2
const val MAX_PLAYERS = 6

val playersPosition = listOf(
    // Player 1 (index 0)
    PlayerPosition(
        xOffset = { w -> (w - PLAYER_SIZE_DP - 8).dp },
        yOffset = { h -> (2 * h / 6).dp }
    ),
    // Player 2 (index 1)
    PlayerPosition(
        xOffset = { w -> (w - (2 * PLAYER_SIZE_DP)).dp },
        yOffset = { h -> (3 * h / 6).dp }
    ),
    // Player 3 (index 2)
    PlayerPosition(
        xOffset = { w -> (w - (3 * PLAYER_SIZE_DP) - 8).dp },
        yOffset = { h -> (3.55 * h / 6).dp }
    ),
    // Player 4 (index 3)
    PlayerPosition(
        xOffset = { w -> (w * 0.2f).dp },
        yOffset = { h -> (3.55 * h / 6).dp }
    ),
    // Player 5 (index 4)
    PlayerPosition(
        xOffset = { w -> (w * 0.1f).dp },
        yOffset = { h -> (3 * h / 6).dp }
    ),
    // Player 6 (index 5)
    PlayerPosition(
        xOffset = { w -> (w * 0.02f).dp },
        yOffset = { h -> (2 * h / 6).dp }
    )
)