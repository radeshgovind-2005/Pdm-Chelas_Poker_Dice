package isel.pdm.pokerdice.vm


import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BaseViewModelTests {

    // Generic Test State and Effect for BaseViewModel
    data class TestState(val count: Int = 0)
    sealed class TestEffect {
        object NavigateToNextScreen : TestEffect()
        data class ShowError(val message: String) : TestEffect()
    }

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

        val viewModel = TestViewModel()

        // Act: Update the state
        viewModel.incrementCount()

        // assert: state is updated
        val state = viewModel.state.first()
        assertEquals(1, state.count)
    }

    @Test
    fun sendEffect_sends_the_correct_effect() = runTest {
        val viewModel = TestViewModel()
        var effect: TestEffect? = null
        val latch = SuspendingLatch() // create a latch to sync the test

        // collect the first emitted effect
        val collectorJob = launch {
            viewModel.effects.collect {
                effect = it
                latch.open() // open the latch when the effect is collected
            }
        }

        // trigger the effect
        viewModel.triggerNavigation()

        // wait for the effect to be emitted
        latch.await()

        // verify that the correct effect was sent
        assertTrue(effect is TestEffect.NavigateToNextScreen)

        collectorJob.cancel()

    }


    @Test
    fun launchWithHandler_handles_errors_and_sends_effect_on_error() = runTest {
        val viewModel = TestViewModel()
        var effect: TestEffect? = null
        val latch = SuspendingLatch() //create a latch to sync the test

        //collect the first emitted effect
        val collectorJob = launch {
            viewModel.effects.collect {
                effect = it
                latch.open()
            }
        }

        // trigger error handling by throwing an exception
        viewModel.triggerError()

        // wait for the effect to be emitted
        latch.await()

        //assert: Verify that the error effect was sent
        assertTrue(effect is TestEffect.ShowError)
        assertEquals("Error: Test error", (effect as TestEffect.ShowError).message)

        collectorJob.cancel()
    }
}
