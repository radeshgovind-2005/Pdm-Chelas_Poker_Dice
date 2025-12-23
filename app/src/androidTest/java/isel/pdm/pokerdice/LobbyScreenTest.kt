package isel.pdm.pokerdice.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.model.lobby.Lobby
import isel.pdm.pokerdice.ui.activities.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyState
import org.junit.Rule
import org.junit.Test

class LobbyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    // Helper to create a dummy lobby
    private fun createLobby(
        players: List<String> = listOf("Host"),
        maxPlayers: Int = 2,
        host: String = "Host"
    ): Lobby {
        return Lobby(
            id = "1",
            name = "Test Lobby",
            description = "Desc",
            host = host,
            state = "WAITING",
            maxRounds = 5,
            maxPlayers = maxPlayers,
            ante = 10,
            initialBalance = 100,
            players = players
        )
    }

    @Test
    fun new_player_sees_join_button_enabled() {
        // Arrange: Not joined, Lobby has space (1/2)
        val lobby = createLobby(players = listOf("Host"), maxPlayers = 2)
        val state = LobbyState(
            lobby = lobby,
            isJoined = false,
            username = "NewPlayer"
        )
        val joinText = getString(R.string.lobby_join)
        var joinClicked = false

        composeTestRule.setContent {
            LobbyScreen(state, {}, { joinClicked = true }, {})
        }

        // Assert
        composeTestRule.onNodeWithText("Test Lobby").assertIsDisplayed()

        composeTestRule.onNodeWithText(joinText)
            .assertIsDisplayed()
            .assertIsEnabled() // Enabled because space exists
            .performClick()

        assert(joinClicked) { "Join callback failed" }
    }

    @Test
    fun joined_player_sees_start_button_disabled() {
        // Arrange: Joined, but I am NOT the host
        val lobby = createLobby(players = listOf("Host", "Me"), maxPlayers = 2, host = "Host")
        val state = LobbyState(
            lobby = lobby,
            isJoined = true,
            username = "Me" // Not Host
        )
        val startText = getString(R.string.lobby_start)

        composeTestRule.setContent {
            LobbyScreen(state, {}, {}, {})
        }

        // Assert
        // Button text changes to "Start Match"
        composeTestRule.onNodeWithText(startText)
            .assertIsDisplayed()
            .assertIsNotEnabled() // Disabled because only Host can start
    }

    @Test
    fun host_sees_start_button_enabled_only_when_full() {
        // Arrange: Joined, I am Host, Lobby is FULL (2/2)
        val lobby = createLobby(players = listOf("Host", "Player2"), maxPlayers = 2, host = "Host")
        val state = LobbyState(
            lobby = lobby,
            isJoined = true,
            username = "Host"
        )
        val startText = getString(R.string.lobby_start)
        var startClicked = false

        composeTestRule.setContent {
            LobbyScreen(state, {}, {}, { startClicked = true })
        }

        // Assert
        composeTestRule.onNodeWithText(startText)
            .assertIsEnabled() // Enabled because Host + Full
            .performClick()

        assert(startClicked) { "Start Match callback failed" }
    }

    @Test
    fun host_sees_start_button_disabled_when_not_full() {
        // Arrange: Joined, I am Host, Lobby NOT full (1/2)
        val lobby = createLobby(players = listOf("Host"), maxPlayers = 2, host = "Host")
        val state = LobbyState(
            lobby = lobby,
            isJoined = true,
            username = "Host"
        )
        val startText = getString(R.string.lobby_start)

        composeTestRule.setContent {
            LobbyScreen(state, {}, {}, {})
        }

        // Assert
        composeTestRule.onNodeWithText(startText).assertIsNotEnabled()
    }
}