package isel.pdm.pokerdice.vm


import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

// Define the generic BaseViewModel tests
class BaseViewModelTests {

    // Generic Test State and Effect for BaseViewModel
    data class TestState(val count: Int = 0)
    sealed class TestEffect {
        object NavigateToNextScreen : TestEffect()
        data class ShowError(val message: String) : TestEffect()
    }

    // Create a Test ViewModel inheriting BaseViewModel
    class TestViewModel : BaseViewModel<TestState, TestEffect>(TestState()) {
        fun incrementCount() {
            setState { copy(count = count + 1) }
        }

        fun triggerNavigation() {
            sendEffect(TestEffect.NavigateToNextScreen)
        }

        fun triggerError() {
            launchWithHandler(onError = { exception ->
                sendEffect(TestEffect.ShowError("Error: ${exception.message}"))
            }) {
                throw TestException("Test error")
            }
        }
    }
    class TestException(message: String) : Exception(message)

    @Test
    fun setState_updates_the_state_correctly() = runTest {
        // Arrange
        val viewModel = TestViewModel()

        // Act: Update the state
        viewModel.incrementCount()

        // Assert: State is updated
        val state = viewModel.state.first()  // Collect first emitted state
        assertEquals(1, state.count) // Expecting count to be incremented
    }

    @Test
    fun sendEffect_sends_the_correct_effect() = runTest {
        // Arrange
        val viewModel = TestViewModel()
        var effect: TestEffect? = null
        val latch = SuspendingLatch() // Create a latch to sync the test

        // Collect the first emitted effect
        val collectorJob = launch {
            viewModel.effects.collect {
                effect = it
                latch.open() // Open the latch when the effect is collected
            }
        }

        // Act: Trigger the effect
        viewModel.triggerNavigation()

        // Wait for the effect to be emitted
        latch.await()

        // Assert: Verify that the correct effect was sent
        assertTrue(effect is TestEffect.NavigateToNextScreen)

        collectorJob.cancel()

    }


    // Test 3: Test that launchWithHandler() handles errors correctly
    @Test
    fun launchWithHandler_handles_errors_and_sends_effect_on_error() = runTest {
        // Arrange
        val viewModel = TestViewModel()
        var effect: TestEffect? = null
        val latch = SuspendingLatch() // Create a latch to sync the test

        // Collect the first emitted effect
        val collectorJob = launch {
            viewModel.effects.collect {
                effect = it
                latch.open() // Open the latch when the effect is collected
            }
        }

        // Act: Trigger error handling by throwing an exception
        viewModel.triggerError()

        // Wait for the effect to be emitted
        latch.await()

        // Assert: Verify that the error effect was sent
        assertTrue(effect is TestEffect.ShowError)
        assertEquals("Error: Test error", (effect as TestEffect.ShowError).message)

        collectorJob.cancel()
    }
}
