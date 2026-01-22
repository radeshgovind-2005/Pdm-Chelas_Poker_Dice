package isel.pdm.pokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.ui.screens.create.CreateScreen
import isel.pdm.pokerdice.ui.viewmodels.create.CreateState
import org.junit.Rule
import org.junit.Test

class CreateScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    @Test
    fun button_enables_when_state_is_valid() {
        // Arrange: Valid state
        val state = CreateState(isCreateEnabled = true)
        val btnText = getString(R.string.create_title)

        composeTestRule.setContent {
            CreateScreen(
                state = state,
                onBackRequest = {},
                onNameChange = {},
                onDescriptionChange = {},
                onExpectedPlayersChange = {},
                onMaxRoundsChange = {},
                onBalanceChange = {},
                onAnteChange = {},
                onCreateRequest = {},
                onTryAgain = {}
            )
        }

        // Assert
        composeTestRule
            .onNode(hasText(btnText) and hasClickAction())
            .assertIsEnabled()
    }

    @Test
    fun error_state_shows_alert_dialog() {
        // Arrange: State with error
        val errorMessage = "Network Error"
        val state = CreateState(error = errorMessage)
        val alertTitle = getString(R.string.alert_error_title)
        val btnText = getString(R.string.alert_error_btn)
        var tryAgainClicked = false

        composeTestRule.setContent {
            CreateScreen(
                state = state,
                onBackRequest = {},
                onNameChange = {},
                onDescriptionChange = {},
                onExpectedPlayersChange = {},
                onMaxRoundsChange = {},
                onBalanceChange = {},
                onAnteChange = {},
                onCreateRequest = {},
                onTryAgain = { tryAgainClicked = true }
            )
        }

        // Assert
        // 1. Dialog Title is visible
        composeTestRule.onNodeWithText(alertTitle).assertIsDisplayed()

        // 2. Error Message is visible
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()

        // 3. Confirm button works
        composeTestRule
            .onNode(hasText(btnText) and hasClickAction())
            .performClick()

        assert(tryAgainClicked) { "Try Again callback failed" }
    }
}