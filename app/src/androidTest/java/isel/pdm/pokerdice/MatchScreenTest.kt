package isel.pdm.pokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.domain.model.match.Game
import isel.pdm.pokerdice.domain.model.match.LobbyContent
import isel.pdm.pokerdice.domain.model.match.MatchContent
import isel.pdm.pokerdice.domain.model.match.MatchPlayers
import isel.pdm.pokerdice.domain.model.match.Round
import isel.pdm.pokerdice.ui.screens.match.MatchScreen
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState
import org.junit.Rule
import org.junit.Test

class MatchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    // --- Helpers to create dummy data ---
    private val testLobby = LobbyContent("L1", "Casino", 100)
    private val testMatch = MatchContent("M1", "RUNNING", true, false)

    private fun createGame(
        round: Round? = null,
        msg: String? = null,
        username: String = "Hero"
    ): Game {
        return Game(
            username = username,
            match = testMatch,
            lobby = testLobby,
            round = round,
            msg = msg
        )
    }

    private fun createPlayer(name: String = "Hero", state: String = "turn", rerolls: Int = 3): MatchPlayers {
        return MatchPlayers(name, "AAAAA", "Rank", 1000, state, rerolls)
    }

    // --- TESTS ---

    @Test
    fun fetching_state_shows_loading_indicator() {
        // Arrange: Game is NULL
        val state = MatchState(game = null)
        val loadingText = getString(R.string.match_fetching)

        composeTestRule.setContent {
            MatchScreen(state, {}, {}, {}, {})
        }

        // Assert: FetchingView is active
        composeTestRule.onNodeWithText(loadingText).assertIsDisplayed()
    }

    @Test
    fun waiting_state_shows_start_round_button() {
        // Arrange: Game exists, but Round is NULL
        val game = createGame(round = null)
        val state = MatchState(game = game)
        val startBtn = getString(R.string.match_wait_btn)
        var startClicked = false

        composeTestRule.setContent {
            MatchScreen(state, { startClicked = true }, {}, {}, {})
        }

        // Assert: WaitingRoundView is active
        composeTestRule.onNodeWithText(startBtn)
            .assertIsDisplayed()
            .performClick()

        assert(startClicked)
    }

    @Test
    fun gameplay_state_shows_roll_button_when_rerolls_available() {
        // Arrange: Round exists, It is "Hero" turn, 3 Rerolls left
        val player = createPlayer(rerolls = 3)
        val round = Round(1, 1, 100, listOf(player))
        val game = createGame(round = round, username = "Hero")

        val state = MatchState(
            game = game,
            currTurn = player // VM updates this
        )
        val rollBtn = getString(R.string.match_roll_btn)

        composeTestRule.setContent {
            MatchScreen(state, {}, {}, {}, {})
        }

        // Assert: PlayView is active, button says "Roll"
        composeTestRule.onNodeWithText(rollBtn)
            .assertIsDisplayed()
            .assertIsEnabled()
    }

    @Test
    fun gameplay_state_shows_hold_button_when_rerolls_empty() {
        // Arrange: 0 Rerolls left -> Button must change to HOLD
        val player = createPlayer(rerolls = 0)
        val round = Round(1, 1, 100, listOf(player))
        val game = createGame(round = round, username = "Hero")

        val state = MatchState(
            game = game,
            currTurn = player
        )
        val holdBtn = getString(R.string.match_hold_btn)

        composeTestRule.setContent {
            MatchScreen(state, {}, {}, {}, {})
        }

        // Assert
        composeTestRule.onNodeWithText(holdBtn).assertIsDisplayed()
    }

    @Test
    fun winner_state_shows_message_and_next_button() {
        // Arrange: Round exists, but 'msg' is NOT NULL (Round Complete)
        val game = createGame(
            round = Round(1, 1, 100, emptyList()),
            msg = "Hero Won 500$!"
        )
        val state = MatchState(game = game)
        val nextBtn = getString(R.string.match_next_btn)
        var nextClicked = false

        composeTestRule.setContent {
            MatchScreen(state, {}, {}, {}, { nextClicked = true })
        }

        // Assert: AnounceRoundWinner is active
        composeTestRule.onNodeWithText("Hero Won 500$!").assertIsDisplayed()

        composeTestRule.onNodeWithText(nextBtn)
            .assertIsDisplayed()
            .performClick()

        assert(nextClicked)
    }
}