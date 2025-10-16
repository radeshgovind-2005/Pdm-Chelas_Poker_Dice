package isel.pdm.chelaspokerdice

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import isel.pdm.chelaspokerdice.screens.lobbies.ADD_BUTTON_VIEW
import isel.pdm.chelaspokerdice.screens.lobbies.LOBBIES_CONTENT_TAG
import isel.pdm.chelaspokerdice.screens.lobbies.LobbiesScreen
import isel.pdm.chelaspokerdice.screens.playerprofile.BACK_BUTTON
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testes instrumentados para o conteúdo do ecrã Lobbies.
 * Verifica apenas o comportamento do composable LobbiesScreenContent.
 */
@RunWith(AndroidJUnit4::class)
class LobbiesScreenContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lobbies_content_is_displayed() {
        composeTestRule.setContent {
            LobbiesScreen().PortraitScreen(Modifier)
        }

        composeTestRule
            .onNodeWithTag(LOBBIES_CONTENT_TAG)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun add_button_is_displayed() {
        composeTestRule.setContent {
            LobbiesScreen().PortraitScreen(Modifier)
        }

        composeTestRule
            .onNodeWithTag(ADD_BUTTON_VIEW)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun lobbies_content_renders_in_landscape_mode() {
        composeTestRule.setContent {
            LobbiesScreen().LandscapeScreen(Modifier)
        }

        composeTestRule
            .onNodeWithTag(LOBBIES_CONTENT_TAG)
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun back_button_is_displayed() {
        composeTestRule.setContent {
            LobbiesScreen().PortraitScreen(Modifier)
        }

        composeTestRule
            .onNodeWithTag(BACK_BUTTON)
            .assertExists()
            .assertIsDisplayed()
    }
}
