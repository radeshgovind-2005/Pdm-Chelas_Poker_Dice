package isel.pdm.pokerdice.ui.viewmodels.title

import android.util.Log
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
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
class TitleViewModelTest {

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
    fun `onLobbiesClicked_navigates_to_Lobbies`() = runTest {
        val sut = TitleViewModel()
        val effects = mutableListOf<TitleNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.onLobbiesClicked()

        assertEquals(1, effects.size)
        assertTrue(effects.first() is TitleNavigation.ToLobbies)
    }

    @Test
    fun `onProfileClicked_navigates_to_Profile`() = runTest {
        val sut = TitleViewModel()
        val effects = mutableListOf<TitleNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.onProfileClicked()

        assertEquals(1, effects.size)
        assertTrue(effects.first() is TitleNavigation.ToProfile)
    }

    @Test
    fun `onAboutClicked_navigates_to_About`() = runTest {
        val sut = TitleViewModel()
        val effects = mutableListOf<TitleNavigation>()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            sut.effects.collect { effects.add(it) }
        }

        sut.onAboutClicked()

        assertEquals(1, effects.size)
        assertTrue(effects.first() is TitleNavigation.ToAbout)
    }
}