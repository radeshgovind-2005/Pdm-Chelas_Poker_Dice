package isel.pdm.pokerdice

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import isel.pdm.pokerdice.screens.about.AboutScreen
import isel.pdm.pokerdice.screens.about.content.ABOUT_CONTENT_TAG
import isel.pdm.pokerdice.screens.about.content.GAMEPLAY_CONTENT_TAG
import isel.pdm.pokerdice.screens.playerprofile.BACK_BUTTON
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AboutScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun back_button_exists() {
        composeTestRule.setContent {
            AboutScreen(
                selectedTabIndex = 0,
                onTabSelected = {},
            ).PortraitScreen(Modifier)
        }

        composeTestRule.onNodeWithTag(BACK_BUTTON).assertExists()
    }


    @Test
    fun clicking_tab_switches_content() {
        composeTestRule.setContent {
            val selectedTab = remember { mutableStateOf(0) }

            AboutScreen(
                selectedTabIndex = selectedTab.value,
                onTabSelected = { index -> selectedTab.value = index }
            ).PortraitScreen(Modifier)
        }
        composeTestRule.onNodeWithTag(GAMEPLAY_CONTENT_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag("TAB_1")
            .assertExists()
            .assertIsDisplayed()
            .performClick()
        composeTestRule.onNodeWithTag(ABOUT_CONTENT_TAG).assertIsDisplayed()
    }


    @Test
    fun landscape_screen_renders_correctly() {
        composeTestRule.setContent {
            AboutScreen(
                selectedTabIndex = 0,
                onTabSelected = {},
            ).LandscapeScreen(Modifier)
        }

        composeTestRule.onNodeWithTag(GAMEPLAY_CONTENT_TAG).assertIsDisplayed()
    }

}