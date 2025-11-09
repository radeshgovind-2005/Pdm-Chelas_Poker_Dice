package isel.pdm.pokerdice

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import isel.pdm.pokerdice.screens.playerprofile.BACK_BUTTON
import isel.pdm.pokerdice.screens.playerprofile.PlayerProfileScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlayerProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun back_button_is_displayed() {
        composeTestRule.setContent {
            PlayerProfileScreen().PortraitScreen(Modifier)
        }
        composeTestRule.onNodeWithTag(BACK_BUTTON).assertExists()
    }

}