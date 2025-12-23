package isel.pdm.pokerdice.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.model.user.UserStats
import isel.pdm.pokerdice.ui.activities.screens.profile.ProfileScreen
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileState
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    @Test
    fun stats_are_displayed_correctly_when_data_loaded() {
        // Arrange
        val mockStats = UserStats(
            gamesPlayed = "10",
            matchesWon = "5",
            winRate = "50%",
            roundsWon = "20",
            lobbiesHosted = "2",
            invitesSent = "0",
            epicHands = "1",
            totalBalance = "1000"
        )
        val username = "PlayerOne"
        val state = ProfileState(
            stats = mockStats,
            username = username,
            isLoading = false
        )

        // Act
        composeTestRule.setContent {
            ProfileScreen(
                state = state,
                onBackClick = {},
                onLogoutRequest = {},
                onLogoutConfirm = {},
                onLogoutCancel = {}
            )
        }

        // Assert
        // 1. Check Header
        val headerText = "${getString(R.string.profile_stats)} $username"
        composeTestRule.onNodeWithText(headerText).assertIsDisplayed()

        // 2. Check Specific Stat Lines
        // matches: "Games Played: 10"
        val gamesPlayedText = "${getString(R.string.profile_g_played)}: 10"
        composeTestRule.onNodeWithText(gamesPlayedText).assertIsDisplayed()

        // matches: "Win Rate: 50%"
        val winRateText = "${getString(R.string.profile_w_rate)}: 50%"
        composeTestRule.onNodeWithText(winRateText).assertIsDisplayed()
    }

    @Test
    fun logout_flow_shows_dialog_and_confirms() {
        // Arrange
        val state = ProfileState(showLogoutDialog = true) // Start with dialog OPEN to test rendering
        var confirmClicked = false
        var cancelClicked = false

        // Act
        composeTestRule.setContent {
            ProfileScreen(
                state = state,
                onBackClick = {},
                onLogoutRequest = {},
                onLogoutConfirm = { confirmClicked = true },
                onLogoutCancel = { cancelClicked = true }
            )
        }

        // Assert
        // 1. Dialog Title is visible
        composeTestRule.onNodeWithText(getString(R.string.profile_logout)).assertIsDisplayed()

        // 2. Click "Yes" (Affirmative)
        composeTestRule.onNodeWithText(getString(R.string.affirmative)).performClick()
        assert(confirmClicked) { "Logout Confirm callback failed" }
    }

    @Test
    fun fab_click_triggers_logout_request() {
        // Arrange
        val state = ProfileState(showLogoutDialog = false)
        var requestClicked = false

        // Act
        composeTestRule.setContent {
            ProfileScreen(
                state = state,
                onBackClick = {},
                onLogoutRequest = { requestClicked = true }, // Capture click
                onLogoutConfirm = {},
                onLogoutCancel = {}
            )
        }

        // Assert
        // Find FAB by Content Description "Logout"
        composeTestRule.onNode(hasContentDescription("Logout")).performClick()
        assert(requestClicked) { "FAB click callback failed" }
    }
}