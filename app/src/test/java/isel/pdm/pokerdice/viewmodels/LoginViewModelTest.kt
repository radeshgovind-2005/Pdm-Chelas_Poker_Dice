package isel.pdm.pokerdice.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.domain.rules.Password
import isel.pdm.pokerdice.domain.rules.Username
import isel.pdm.pokerdice.domain.model.user.User
import isel.pdm.pokerdice.domain.usecases.AuthUseCase
import isel.pdm.pokerdice.ui.viewmodels.login.LoginNavigation
import isel.pdm.pokerdice.ui.viewmodels.login.LoginState
import isel.pdm.pokerdice.ui.viewmodels.login.LoginViewModel
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCase = mockk<AuthUseCase>()

    // Define VALID constants that satisfy your strict Domain Rules
    // Username: "Alice" -> Capital start, no digits
    private val validNameStr = "Alice"
    private val validUsername = Username(validNameStr)

    // Password: "Pass1" -> Length > 4, Capital 'P', Digit '1'
    private val validPassStr = "Pass1"
    private val validPassword = Password(validPassStr)

    @Before
    fun setup() {
        // Mock Android Log to prevent crash
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.w(any(), any<String>()) } returns 0
        every { Log.w(any(), any<Throwable>()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `initial_state_is_idle_with_empty_credentials`() = runTest {
        val sut = LoginViewModel(SavedStateHandle(), useCase)
        val state = sut.state.value

        assertEquals("", state.username)
        assertEquals("", state.pass)
        assertFalse(state.isLoading)
        assertNull(state.usernameError)
        assertNull(state.passError)
        assertFalse(state.isLoginEnabled)
    }

    @Test
    fun `signIn_is_disabled_when_credentials_invalid`() = runTest {
        val sut = LoginViewModel(SavedStateHandle(), useCase)

        // Scenario 1: Password Valid, Username Empty
        sut.onPasswordChange(validPassStr)
        assertFalse("Should be disabled (empty user)", sut.state.value.isLoginEnabled)

        // Scenario 2: Username Valid, Password Empty
        sut.onUsernameChange(validNameStr)
        sut.onPasswordChange("")
        assertFalse("Should be disabled (empty pass)", sut.state.value.isLoginEnabled)
    }

    @Test
    fun `signIn_success_flow_updates_state_and_navigates`() = runTest {
        // Arrange
        val sut = LoginViewModel(SavedStateHandle(), useCase)
        val mockUser = mockk<User>()

        // CRITICAL FIX: Use specific instances (validUsername, validPassword)
        // instead of any(). This prevents MockK from generating invalid dummy data.
        coEvery {
            useCase.performLogin(validUsername, validPassword)
        } returns Result.success(mockUser)

        val states = mutableListOf<LoginState>()
        val effects = mutableListOf<LoginNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        // Act
        sut.onUsernameChange(validNameStr)
        sut.onPasswordChange(validPassStr)

        // Ensure button is enabled before clicking
        assertTrue("Login button should be enabled", sut.state.value.isLoginEnabled)

        sut.signIn()

        // Assert
        assertTrue("Should have entered loading state", states.any { it.isLoading })

        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertNull(finalState.passError)

        assertEquals(1, effects.size)
        assertTrue(effects.first() is LoginNavigation.ToTitle)
    }

    @Test
    fun `signIn_failure_updates_error_state`() = runTest {
        // Arrange
        val sut = LoginViewModel(SavedStateHandle(), useCase)
        val errorMsg = "Bad Credentials"

        // CRITICAL FIX: Use specific instances here too
        coEvery {
            useCase.performLogin(validUsername, validPassword)
        } returns Result.failure(Exception(errorMsg))

        val states = mutableListOf<LoginState>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.state.collect { states.add(it) }
        }

        // Act
        sut.onUsernameChange(validNameStr)
        sut.onPasswordChange(validPassStr)
        sut.signIn()

        // Assert
        val finalState = states.last()
        assertFalse(finalState.isLoading)
        assertEquals(errorMsg, finalState.passError)
    }
}