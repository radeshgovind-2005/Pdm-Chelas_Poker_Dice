package isel.pdm.pokerdice

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import isel.pdm.pokerdice.ui.viewmodels.main.MainState
import org.junit.Rule
import org.junit.Test
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.ui.screens.main.MainScreen

class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun main_screen_displays_app_title_correctly() {
        // Arrange
        val state = MainState(isLoading = true)

        // Get the actual string value from resources to be robust
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val expectedTitle = context.getString(R.string.main_text)

        // Act
        composeTestRule.setContent {
            MainScreen(state = state)
        }

        // Assert
        composeTestRule.onNodeWithText(expectedTitle).assertIsDisplayed()
    }

    @Test
    fun main_screen_shows_progress_indicator_when_loading() {
        // Arrange
        val state = MainState(isLoading = true)

        // Act
        composeTestRule.setContent {
            MainScreen(state = state)
        }

        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }
}