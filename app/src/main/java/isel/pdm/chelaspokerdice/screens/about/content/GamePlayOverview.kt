package isel.pdm.chelaspokerdice.screens.about.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.components.elements.LinkText
import isel.pdm.chelaspokerdice.components.elements.SimpleText
import isel.pdm.chelaspokerdice.components.elements.TitleText
import isel.pdm.chelaspokerdice.components.struct.tabs.SimpleCard

const val GAMEPLAY_OVERVIEW =  "Poker Dice is played with 5 special dice featuring playing card suits (9, 10, Jack, Queen, King, Ace) instead of numbers. " +
        "Players roll the dice and try to form the best poker hand possible. Each player typically gets up to 3 rolls per turn, " +
        "choosing which dice to keep and which to re-roll. After the final roll, the player with the highest-ranking poker hand wins the round. " +
        "Common hands include pairs, three/four of a kind, full house, and five of a kind."

@Composable
fun GamePlayOverview(onNavigateToUri: (String) -> Unit) {
    SimpleCard {
        TitleText("Gameplay Overview")
        Spacer(modifier = Modifier.height(8.dp))
        SimpleText(GAMEPLAY_OVERVIEW)
        LinkText("Learn more on Wikipedia") { onNavigateToUri(GAME_RULES_LINK) }
    }
}