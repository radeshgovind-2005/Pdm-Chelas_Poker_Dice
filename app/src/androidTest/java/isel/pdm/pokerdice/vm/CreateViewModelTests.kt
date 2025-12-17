//package isel.pdm.pokerdice.vm
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import isel.pdm.pokerdice.ui.viewmodels.create.CreateNavigation
//import isel.pdm.pokerdice.ui.viewmodels.create.CreateState
//import isel.pdm.pokerdice.ui.viewmodels.create.CreateViewModel
//import isel.pdm.pokerdice.ui.viewmodels.usecases.CreateUseCase
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.*
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.kotlin.*
//
//@ExperimentalCoroutinesApi
//class CreateViewModelTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()  // Necessário para o LiveData funcionar em testes
//
//    private lateinit var viewModel: CreateViewModel
//    private lateinit var createUseCase: CreateUseCase
//    private lateinit var stateObserver: Observer<CreateState>
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    @Before
//    fun setUp() {
//        createUseCase = mock()  // Mock do usecase
//
//        // Inicializando o SavedStateHandle e ViewModel
//        val savedStateHandle = mock<SavedStateHandle>()
//        viewModel = CreateViewModel(savedStateHandle, createUseCase)
//
//        stateObserver = mock()  // Mock do observer
//        viewModel.state.observeForever(stateObserver)
//    }
//
//    @Test
//    fun test_validateName_updates_state_with_error_when_name_is_invalid() = runTest {
//        // Arrange: configure mock responses
//        val invalidName = "invalid"
//        val expectedState = CreateState(nameError = "Invalid name")
//
//        // Act: simula mudança de nome
//        viewModel.onNameChange(invalidName)
//
//        // Assert: Verifica se o estado foi atualizado corretamente
//        verify(stateObserver).onChanged(expectedState)
//    }
//
//    @Test
//    fun test_onCreateLobby_success_creates_lobby_and_navigates_to_lobby_screen() = runTest {
//        // Arrange
//        val validState = CreateState(
//            name = "Test Lobby",
//            description = "Test Description",
//            expectedPlayer = 4,
//            maxRounds = 10,
//            balance = 1000,
//            ante = 50,
//            isCreateEnabled = true
//        )
//
//        // Mock do resultado do useCase
//        val mockLobbyId = "12345"
//        whenever(createUseCase.createLobby(
//            validState.name,
//            validState.description,
//            validState.expectedPlayer,
//            validState.maxRounds,
//            validState.balance,
//            validState.ante
//        )).thenReturn(Result.success(mockLobbyId))
//
//        // Act
//        viewModel.setState { validState }
//        viewModel.onCreateLobby()
//
//        // Assert: Verifique se o efeito de navegação foi enviado
//        verify(createUseCase).createLobby(
//            validState.name,
//            validState.description,
//            validState.expectedPlayer,
//            validState.maxRounds,
//            validState.balance,
//            validState.ante
//        )
//
//        verify(stateObserver).onChanged(validState.copy(isLoading = false))
//        // Verifica se o efeito de navegação foi disparado corretamente
//        verify(viewModel).sendEffect(CreateNavigation.ToLobby(mockLobbyId))
//    }
//
//    @Test
//    fun test_onCreateLobby_failure_shows_error_message() = runTest {
//        // Arrange
//        val validState = CreateState(
//            name = "Test Lobby",
//            description = "Test Description",
//            expectedPlayer = 4,
//            maxRounds = 10,
//            balance = 1000,
//            ante = 50,
//            isCreateEnabled = true
//        )
//
//        // Mock do erro
//        val errorMessage = "Network error"
//        whenever(createUseCase.createLobby(
//            validState.name,
//            validState.description,
//            validState.expectedPlayer,
//            validState.maxRounds,
//            validState.balance,
//            validState.ante
//        )).thenReturn(Result.failure(Exception(errorMessage)))
//
//        // Act
//        viewModel.setState { validState }
//        viewModel.onCreateLobby()
//
//        // Assert
//        verify(stateObserver).onChanged(validState.copy(isLoading = false, error = errorMessage))
//    }
//}
