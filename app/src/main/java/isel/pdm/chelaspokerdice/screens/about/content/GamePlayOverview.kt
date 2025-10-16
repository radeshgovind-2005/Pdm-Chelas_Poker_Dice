package isel.pdm.chelaspokerdice.screens.about.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.ui.components.elements.LinkText
import isel.pdm.chelaspokerdice.ui.components.elements.SimpleText
import isel.pdm.chelaspokerdice.ui.components.elements.TitleText
import isel.pdm.chelaspokerdice.ui.components.struct.tabs.SimpleCard



const val GAMEPLAY_CONTENT_TAG = "GAMEPLAY_CONTENT"

@Composable
fun GamePlayOverview(onNavigateToUri: (String) -> Unit) {
    SimpleCard(
        modifier = Modifier.testTag(GAMEPLAY_CONTENT_TAG)
    ) {
        TitleText("Gameplay Overview")
        Spacer(modifier = Modifier.height(8.dp))
        SimpleText(stringResource(R.string.game_play_overview))
        LinkText("Learn more on Wikipedia") { onNavigateToUri(GAME_RULES_LINK) }
    }
}