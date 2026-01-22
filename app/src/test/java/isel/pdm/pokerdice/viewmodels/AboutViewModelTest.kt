package isel.pdm.pokerdice.viewmodels

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import isel.pdm.pokerdice.ui.viewmodels.about.AboutNavigation
import isel.pdm.pokerdice.ui.viewmodels.about.AboutViewModel
import isel.pdm.pokerdice.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AboutViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `onBackRequest_navigates_to_Title`() = runTest {
        val sut = AboutViewModel()
        val effects = mutableListOf<AboutNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.onBackRequest()

        assertEquals(1, effects.size)
        assertTrue(effects.first() is AboutNavigation.ToTitle)
    }

    @Test
    fun `onMailRequest_navigates_to_Mail_with_correct_data`() = runTest {
        val sut = AboutViewModel()
        val effects = mutableListOf<AboutNavigation>()
        val recipients = listOf("dev@chelas.com")
        val subject = "Feedback"

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.onMailRequest(recipients, subject)

        assertEquals(1, effects.size)
        val effect = effects.first() as AboutNavigation.ToMail
        assertEquals(recipients, effect.sendTo)
        assertEquals(subject, effect.subject)
    }

    @Test
    fun `onWebRequest_navigates_to_Web_with_correct_uri`() = runTest {
        val sut = AboutViewModel()
        val effects = mutableListOf<AboutNavigation>()
        val uri = "https://chelas.poker"

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.onWebRequest(uri)

        assertEquals(1, effects.size)
        val effect = effects.first() as AboutNavigation.ToWeb
        assertEquals(uri, effect.uri)
    }
}