package isel.pdm.pokerdice

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.ui.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.viewmodels.title.TitleState
import org.junit.Rule
import org.junit.Test

class TitleScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    @Test
    fun title_screen_displays_main_content_correctly() {
        // Arrange
        val state = TitleState()
        val btnText = getString(R.string.title_btn)
        val titleText = getString(R.string.game_name)
        var browseClicked = false

        // Act
        composeTestRule.setContent {
            TitleScreen(
                state = state,
                onClickAbout = {},
                onClickLobbies = { browseClicked = true }, // Capture click
                onClickProfile = {}
            )
        }

        // Assert
        // 1. Verify App Title matches resource
        composeTestRule.onNodeWithText(titleText).assertIsDisplayed()

        // 2. Verify Main Button works
        composeTestRule.onNodeWithText(btnText)
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()

        assert(browseClicked) { "Main button click did not trigger callback" }
    }

    @Test
    fun drawer_opens_and_navigates_correctly() {
        // Arrange
        val state = TitleState()
        val profileText = getString(R.string.title_item1) // "Profile"
        val aboutText = getString(R.string.title_item2)   // "About"

        var profileClicked = false
        var aboutClicked = false

        // Act
        composeTestRule.setContent {
            TitleScreen(
                state = state,
                onClickAbout = { aboutClicked = true },
                onClickLobbies = {},
                onClickProfile = { profileClicked = true }
            )
        }

        // 1. Find Menu Button (The 'Hamburger' icon)
        val menuNode = composeTestRule.onNode(hasContentDescription("Menu"))

        menuNode.assertIsDisplayed().performClick()

        // 2. Verify Drawer Opened by finding Profile Text
        val profileNode = composeTestRule.onNodeWithText(profileText)
        profileNode.assertIsDisplayed()

        // 3. Test Profile Click
        profileNode.performClick()
        assert(profileClicked) { "Profile item click failed" }

        // 4. Re-open Drawer (it closes on selection usually) and Test About
        menuNode.performClick()
        composeTestRule.onNodeWithText(aboutText).performClick()
        assert(aboutClicked) { "About item click failed" }
    }
}