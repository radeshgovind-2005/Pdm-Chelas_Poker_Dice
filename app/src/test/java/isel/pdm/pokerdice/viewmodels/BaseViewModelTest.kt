package isel.pdm.pokerdice.viewmodels

import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    class TestViewModel : BaseViewModel<String, String>("InitialState") {
        fun updateState(newState: String) = setState { newState }
        fun triggerEffect(effect: String) = sendEffect(effect)

        fun runSafeOperation(
            shouldThrow: Boolean = false,
            shouldCancel: Boolean = false,
            onError: (Throwable) -> Unit
        ) {
            launchWithHandler(onError) {
                if (shouldCancel) throw CancellationException("Scope cancelled")
                if (shouldThrow) throw IOException("Network failed")
                setState { "Success" }
            }
        }
    }

    @Test
    fun initial_state_is_correct() = runTest {
        val sut = TestViewModel()
        assertEquals("InitialState", sut.state.value)
    }

    @Test
    fun setState_updates_the_state_flow() = runTest {
        val sut = TestViewModel()

        sut.updateState("NewState")

        assertEquals("NewState", sut.state.value)
    }

    @Test
    fun sendEffect_emits_effect_to_collectors() = runTest {
        val sut = TestViewModel()
        val receivedEffects = mutableListOf<String>()

        // We launch a collector in the backgroundScope (automatically cancelled at end of test)
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { receivedEffects.add(it) }
        }

        sut.triggerEffect("NavigateHome")

        assertEquals(1, receivedEffects.size)
        assertEquals("NavigateHome", receivedEffects[0])
    }

    @Test
    fun launchWithHandler_catches_generic_exceptions_and_forwards_to_onError() = runTest {
        val sut = TestViewModel()
        var errorReceived: Throwable? = null

        sut.runSafeOperation(shouldThrow = true, onError = { errorReceived = it })

        assertTrue(errorReceived is IOException)
        assertEquals("Network failed", errorReceived?.message)
    }

    @Test
    fun `launchWithHandler_re-throws_CancellationException_correctly`() = runTest {
        val sut = TestViewModel()
        var errorReceived: Throwable? = null

        try {
            sut.runSafeOperation(
                shouldCancel = true,
                onError = { errorReceived = it }
            )
        } catch (e: CancellationException) {
            // If we catch this, it means BaseViewModel correctly re-threw the exception!
            // This is the expected behavior.
        }

        // Verify that the exception bypassed the onError callback
        assertTrue(
            "The CancellationException should not have been passed to onError",
            errorReceived == null
        )
    }
}