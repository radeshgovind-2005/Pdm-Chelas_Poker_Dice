//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import isel.pdm.pokerdice.domain.lobby.BrowseLobby
//import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseState
//import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseViewModel
//import isel.pdm.pokerdice.services.events.LobbiesEvents
//import isel.pdm.pokerdice.ui.viewmodels.usecases.BrowseUseCase
//import isel.pdm.pokerdice.vm.SuspendingLatch
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.test.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito
//import org.mockito.kotlin.verify
//
//@ExperimentalCoroutinesApi
//class BrowseViewModelTests {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: BrowseViewModel
//    private lateinit var navigationObserver: Observer<BrowseState>
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    private val usecase: BrowseUseCase = mock()
//
//    @Before
//    fun setUp() {
//        viewModel = BrowseViewModel(usecase)
//        navigationObserver = mock()  // Criando o mock do observer
//        viewModel.state.observeForever(navigationObserver)
//    }
//
//    // Função para testar eventos comuns (Add, Init, Remove, Error)
//    private suspend fun testEvent(
//        event: LobbiesEvents,
//        expectedState: BrowseState
//    ) {
//        val latch = SuspendingLatch()
//
//        `when`(usecase.subscribeToLobbies()).thenReturn(
//            flow {
//            emit(event)
//            latch.open()  // Liberando o latch após emitir o evento
//        })
//
//        viewModel.subscribeToLobbies()
//
//        latch.await()
//
//        verify(navigationObserver).onChanged(expectedState)
//    }
//
//    @Test
//    fun testSubscribeToLobbies_AddEvent() = runTest {
//        val lobby = BrowseLobby(id = "1", name = "Test Lobby", rounds = 2, hostName = "Martim")
//        val expectedState = BrowseState(
//            isLoading = false,
//            lobbies = listOf(lobby),
//            filteredLobbies = listOf(lobby)
//        )
//
//        testEvent(LobbiesEvents.Add(lobby), expectedState)
//    }
//
//    @Test
//    fun testSubscribeToLobbies_InitEvent() = runTest {
//        val expectedState = BrowseState(
//            isLoading = false,
//            lobbies = emptyList(),
//            filteredLobbies = emptyList()
//        )
//
//        testEvent(LobbiesEvents.Init(emptyList()), expectedState)
//    }
//
//    @Test
//    fun testSubscribeToLobbies_RemoveEvent() = runTest {
//        val lobby = BrowseLobby(id = "1", name = "Test Lobby", hostName = "Host")
//        val initialLobbies = listOf(lobby)
//
//        val expectedState = BrowseState(
//            isLoading = false,
//            lobbies = emptyList(),
//            filteredLobbies = emptyList()
//        )
//
//        `when`(usecase.subscribeToLobbies()).thenReturn(flow {
//            emit(LobbiesEvents.Init(initialLobbies))
//            emit(LobbiesEvents.Remove(lobby.id))
//            latch.open()  // Liberando o latch após emitir os eventos
//        })
//
//        viewModel.subscribeToLobbies()
//
//        latch.await()
//
//        verify(navigationObserver).onChanged(expectedState)
//    }
//
//    @Test
//    fun testSubscribeToLobbies_ErrorEvent() = runTest {
//        val errorMessage = "An error occurred"
//
//        val expectedState = BrowseState(
//            isLoading = false,
//            error = errorMessage,
//            lobbies = emptyList(),
//            filteredLobbies = emptyList()
//        )
//
//        testEvent(LobbiesEvents.Error(errorMessage), expectedState)
//    }
//}
