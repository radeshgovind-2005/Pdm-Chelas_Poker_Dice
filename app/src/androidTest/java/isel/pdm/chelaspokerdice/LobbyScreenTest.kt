package isel.pdm.chelaspokerdice

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import isel.pdm.chelaspokerdice.screens.lobbies.ADD_BUTTON_VIEW
import isel.pdm.chelaspokerdice.ui.components.figures.icons.AddIcon
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LobbyScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun add_button_is_displayed() {
        composeTestRule.setContent {
            AddIcon()
        }
        composeTestRule.onNodeWithTag(ADD_BUTTON_VIEW).assertExists()

    }
}