package isel.pdm.pokerdice.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.lobby.BrowseLobby
import isel.pdm.pokerdice.ui.activities.screens.browse.BrowseLobbiesScreen
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseState
import org.junit.Rule
import org.junit.Test

class BrowseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    @Test
    fun loading_state_shows_progress_indicator() {
        val state = BrowseState(isLoading = true)

        composeTestRule.setContent {
            BrowseLobbiesScreen(state, {}, {}, {}, {}, {})
        }

        composeTestRule
            .onNode(hasProgressBarRangeInfo(androidx.compose.ui.semantics.ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun empty_state_shows_no_lobbies_message() {
        // Arrange: Not loading, Empty List
        val state = BrowseState(
            isLoading = false,
            filteredLobbies = emptyList()
        )
        val noLobbiesMsg = getString(R.string.browse_no_lobbies)

        // Act
        composeTestRule.setContent {
            BrowseLobbiesScreen(state, {}, {}, {}, {}, {})
        }

        // Assert
        composeTestRule.onNodeWithText(noLobbiesMsg).assertIsDisplayed()
    }

    @Test
    fun populated_state_shows_lobby_cards_and_handles_clicks() {
        // Arrange
        val lobby = BrowseLobby("123", "High Stakes", 10, "Bond")
        val state = BrowseState(
            isLoading = false,
            filteredLobbies = listOf(lobby)
        )
        var lobbyIdClicked = ""

        // Act
        composeTestRule.setContent {
            BrowseLobbiesScreen(
                state = state,
                onBackClick = {},
                onSearch = {},
                onQueryChange = {},
                onCreateLobby = {},
                onLobbyClick = { lobbyIdClicked = it }
            )
        }

        // Assert
        // 1. Verify Card Content
        composeTestRule.onNodeWithText("High Stakes").assertIsDisplayed()
        // Check host text (e.g. "Host: Bond")
        val hostText = "${getString(R.string.browse_host)}: Bond"
        composeTestRule.onNodeWithText(hostText).assertIsDisplayed()

        // 2. Click the Card
        composeTestRule.onNodeWithText("High Stakes").performClick()
        assert(lobbyIdClicked == "123") { "Lobby click returned wrong ID" }
    }

    @Test
    fun search_bar_accepts_input() {
        val state = BrowseState(query = "")
        var queryInput = ""
        val placeholder = getString(R.string.browse_placeholder)

        composeTestRule.setContent {
            BrowseLobbiesScreen(
                state = state,
                onBackClick = {},
                onSearch = {},
                onQueryChange = { queryInput = it },
                onCreateLobby = {},
                onLobbyClick = {}
            )
        }

        // Find Search Bar by Placeholder text
        composeTestRule.onNodeWithText(placeholder).performTextInput("Poker")

        assert(queryInput == "Poker") { "Query change callback failed" }
    }

    @Test
    fun fab_triggers_create_event() {
        val state = BrowseState()
        var createClicked = false

        composeTestRule.setContent {
            BrowseLobbiesScreen(
                state = state,
                onBackClick = {},
                onSearch = {},
                onQueryChange = {},
                onCreateLobby = { createClicked = true },
                onLobbyClick = {}
            )
        }

        // Find FAB by Content Description "Create"
        composeTestRule.onNode(hasContentDescription("Create")).performClick()
        assert(createClicked) { "FAB click callback failed" }
    }
}
