package isel.pdm.pokerdice

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import isel.pdm.pokerdice.ui.screens.login.LoginScreen
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun getString(id: Int): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(id)
    }

    @Test
    fun login_screen_initial_state_is_correct() {
        val state = LoginState()
        val loginText = getString(R.string.login_title)

        composeTestRule.setContent {
            LoginScreen(state, {}, {}, {})
        }

        // 1. Verify Title Exists (It has text, but NO click action)
        composeTestRule
            .onNode(hasText(loginText) and !hasClickAction())
            .assertIsDisplayed()

        // 2. Verify Button is Disabled (It has text AND click action)
        composeTestRule
            .onNode(hasText(loginText) and hasClickAction())
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun login_button_enables_when_state_permits() {
        val state = LoginState(
            username = "ValidUser",
            pass = "ValidPass",
            isLoginEnabled = true
        )
        val loginText = getString(R.string.login_title)

        composeTestRule.setContent {
            LoginScreen(state, {}, {}, {})
        }

        // Verify Button is ENABLED
        composeTestRule
            .onNode(hasText(loginText) and hasClickAction())
            .assertIsEnabled()
    }

    @Test
    fun loading_state_replaces_button_with_spinner() {
        val state = LoginState(isLoading = true)
        val loginText = getString(R.string.login_title)

        composeTestRule.setContent {
            LoginScreen(state, {}, {}, {})
        }

        // 1. Progress Bar is Visible
        composeTestRule
            .onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()

        // 2. Button Should NOT Exist
        // Crucial: We must check for (Text + ClickAction).
        // If we just checked Text, it would fail because the Title is still there.
        composeTestRule
            .onNode(hasText(loginText) and hasClickAction())
            .assertDoesNotExist()
    }

    @Test
    fun input_fields_accept_text_events() {
        var usernameInput = ""

        composeTestRule.setContent {
            LoginScreen(
                state = LoginState(),
                onUsernameChange = { usernameInput = it },
                onPasswordChange = {},
                onLoginClick = {}
            )
        }

        val input = "MyUser"

        // Find the first text field (Username)
        // We use 'onAllNodes' and pick the first one, which is safe for this specific screen layout
        composeTestRule
            .onAllNodes(hasSetTextAction())[0]
            .performTextInput(input)

        // Assert callback fired
        assert(usernameInput == input) {
            "Expected '$input' but callback received '$usernameInput'"
        }
    }
}