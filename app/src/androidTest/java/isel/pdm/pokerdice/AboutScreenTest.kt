package isel.pdm.pokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.ui.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.viewmodels.about.AboutState
import org.junit.Rule
import org.junit.Test

class AboutScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    @Test
    fun project_page_displayed_by_default_and_handles_contact_click() {
        val state = AboutState()
        val projectTitle = getString(R.string.profile_p2_title) // Page 0
        val contactBtn = getString(R.string.profile_p2_btn)
        var mailClicked = false

        composeTestRule.setContent {
            AboutScreen(
                state = state,
                onBackClick = {},
                onMailClick = { _, _ -> mailClicked = true },
                onWebRequest = {}
            )
        }

        composeTestRule.onNodeWithText(projectTitle).assertIsDisplayed()

        composeTestRule.onNodeWithText(contactBtn)
            .assertIsDisplayed()
            .performClick()

        assert(mailClicked) { "Mail click callback was not triggered" }
    }

    @Test
    fun swipe_reveals_gameplay_page_and_handles_wiki_click() {
        // Arrange
        val state = AboutState()

        // We need the Page 0 title to use as a "Swipe Handle"
        val projectTitle = getString(R.string.profile_p2_title) // Page 0 Title

        val gameplayTitle = getString(R.string.profile_p1_title) // Page 1 Title
        val wikiBtn = getString(R.string.profile_p1_btn)
        var webClicked = false

        composeTestRule.setContent {
            AboutScreen(
                state = state,
                onBackClick = {},
                onMailClick = { _, _ -> },
                onWebRequest = { webClicked = true }
            )
        }

        // 1. Verify we start on Page 0
        val page0Node = composeTestRule.onNodeWithText(projectTitle)
        page0Node.assertIsDisplayed()

        // 2. Act: Swipe Left ON THE CONTENT of Page 0
        // This ensures the Pager receives the gesture.
        page0Node.performTouchInput { swipeLeft() }

        // 3. Wait for the Pager "snap" animation to finish
        composeTestRule.waitForIdle()

        // Assert: Page 1 Title is now visible
        composeTestRule.onNodeWithText(gameplayTitle).assertIsDisplayed()

        // Assert: Wiki button works
        composeTestRule.onNodeWithText(wikiBtn)
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        assert(webClicked) { "Web click callback was not triggered after swipe" }
    }
}