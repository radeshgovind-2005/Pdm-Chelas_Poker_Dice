package isel.pdm.pokerdice.ui.viewmodels.create

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.rules.LobbyAnte
import isel.pdm.pokerdice.domain.rules.LobbyBalance
import isel.pdm.pokerdice.domain.rules.LobbyDescription
import isel.pdm.pokerdice.domain.rules.LobbyExpectedPlayers
import isel.pdm.pokerdice.domain.rules.LobbyName
import isel.pdm.pokerdice.domain.rules.LobbyRounds
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.domain.usecases.CreateUseCase

@Suppress("DEPRECATION")
class CreateViewModel (
    private val savedStateHandle: SavedStateHandle,
    private val usecase: CreateUseCase
) : BaseViewModel<CreateState, CreateNavigation>(CreateState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {

        private const val KEY_NAME = "name"
        private const val KEY_DESC = "description"
        private const val KEY_PLAYERS = "players"
        private const val KEY_ROUNDS = "rounds"
        private const val KEY_BALANCE = "balance"
        private const val KEY_ANTE = "ante"

        fun provideFactory(
            owner: SavedStateRegistryOwner,
            usecase: CreateUseCase
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, null) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T = if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
                    CreateViewModel(handle, usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
            }
    }
    init {
        // Restore state from SavedStateHandle if available
        val savedName = savedStateHandle.get<String>(KEY_NAME) ?: ""
        val savedDesc = savedStateHandle.get<String>(KEY_DESC) ?: ""
        val savedPlayers = savedStateHandle.get<Int>(KEY_PLAYERS)
        val savedRounds = savedStateHandle.get<Int>(KEY_ROUNDS)
        val savedBalance = savedStateHandle.get<Int>(KEY_BALANCE)
        val savedAnte = savedStateHandle.get<Int>(KEY_ANTE)

        // Initialize state and validate immediately to set initial errors/enabled state
        setState {
            copy(
                name = savedName,
                description = savedDesc,
                expectedPlayer = savedPlayers,
                maxRounds = savedRounds,
                balance = savedBalance,
                ante = savedAnte
            )
        }
        validateAll()
    }

    // --- Input Handlers ---

    fun onNameChange(input: String) {
        savedStateHandle[KEY_NAME] = input
        setState { copy(name = input) }
        validateName()
        checkEnabled()
    }

    fun onDescriptionChange(input: String) {
        savedStateHandle[KEY_DESC] = input
        setState { copy(description = input) }
        validateDescription()
        checkEnabled()
    }

    fun onExpectedPlayersChange(input: Int) {
        savedStateHandle[KEY_PLAYERS] = input
        setState { copy(expectedPlayer = input) }
        validatePlayers()
        // Rounds validity depends on Player count (multiple of), so re-validate rounds
        validateRounds()
        checkEnabled()
    }

    fun onMaxRoundsChange(input: Int) {
        savedStateHandle[KEY_ROUNDS] = input
        setState { copy(maxRounds = input) }
        validateRounds()
        checkEnabled()
    }

    fun onBalanceChange(input: Int) {
        savedStateHandle[KEY_BALANCE] = input
        setState { copy(balance = input) }
        validateBalance()
        checkEnabled()
    }

    fun onAnteChange(input: Int) {
        savedStateHandle[KEY_ANTE] = input
        setState { copy(ante = input) }
        validateAnte()
        checkEnabled()
    }

    // --- Validation Logic ---

    private fun validateName() {
        val error = LobbyName.isValid(state.value.name)
        setState { copy(nameError = error) }
    }

    private fun validateDescription() {
        val error = LobbyDescription.isValid(state.value.description)
        setState { copy(descriptionError = error) }
    }

    private fun validatePlayers() {
        val players = state.value.expectedPlayer
        val error = if (players == null) "Required" else LobbyExpectedPlayers.isValid(players)
        setState { copy(expectedPlayerError = error) }
    }

    private fun validateRounds() {
        val rounds = state.value.maxRounds
        val players = state.value.expectedPlayer ?: 0

        val error = if (rounds == null) {
            "Required"
        } else {
            // Rounds validation depends on the number of players
            LobbyRounds.isValid(rounds, players)
        }
        setState { copy(maxRoundsError = error) }
    }

    private fun validateBalance() {
        val balance = state.value.balance
        val error = if (balance == null) "Required" else LobbyBalance.isValid(balance)
        setState { copy(balanceError = error) }
    }

    private fun validateAnte() {
        val ante = state.value.ante
        val balance = state.value.balance ?: 0
        val error = if (ante == null) "Required" else LobbyAnte.isValid(ante)
        val relationshipError = if (error == null && ante != null && balance != null) {
            if (ante >= balance) {
                "Ante must be less than Balance"
            } else {
                null
            }
        } else {
            null
        }
        setState { copy(anteError = error ?: relationshipError) }
    }

    fun onTryAgain(){
        logger.i("Trying again to create lobby")
        setState { copy(error=null) }
    }
    private fun validateAll() {
        validateName()
        validateDescription()
        validatePlayers()
        validateRounds()
        validateBalance()
        validateAnte()
        checkEnabled()
    }

    private fun checkEnabled() {
        val s = state.value
        val hasErrors = s.nameError != null || s.descriptionError != null ||
                s.expectedPlayerError != null || s.maxRoundsError != null ||
                s.balanceError != null || s.anteError != null

        val hasEmptyFields = s.name.isEmpty() || s.description.isEmpty() ||
                s.expectedPlayer == null || s.maxRounds == null ||
                s.balance == null || s.ante == null

        setState { copy(isCreateEnabled = !hasErrors && !hasEmptyFields) }
    }

    fun onCreateLobby() {
        if (!state.value.isCreateEnabled) return

        logger.i("Attempting to create lobby: ${state.value.name}")
        setState { copy(isLoading = true, error = null) }

        launchWithHandler(
            onError = { e ->
                logger.e("Create lobby crashed", e)
                setState { copy(isLoading = false, error = e.message ?: "Unknown Error") }
            }
        ) {
            usecase.createLobby(
                state.value.name,
                state.value.description,
                state.value.expectedPlayer,
                state.value.maxRounds,
                state.value.balance,
                state.value.ante,
            ).fold(
                onSuccess = { lobbyId ->
                    logger.i("Lobby created successfully: $lobbyId")
                    setState { copy(isLoading = false) }
                    sendEffect(CreateNavigation.ToLobby(lobbyId) )
                },
                onFailure = { e ->
                    logger.w("Create lobby failed: ${e.message}")
                    setState { copy(isLoading = false, error = e.message) }
                }
            )
        }
    }

    fun toBackRequest(){
        sendEffect(CreateNavigation.ToBrowse)
    }
}