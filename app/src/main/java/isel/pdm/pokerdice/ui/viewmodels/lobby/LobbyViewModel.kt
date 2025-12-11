package isel.pdm.pokerdice.ui.viewmodels.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.services.events.LobbyEvent
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.usecases.LobbyUseCase

class LobbyViewModel(
    private val usecase: LobbyUseCase
) : BaseViewModel<LobbyState, LobbyNavigation>(LobbyState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: LobbyUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(LobbyViewModel::class.java)) {
                    LobbyViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun inititializeVM(lobbyId: String){
        setState { copy(lobbyId=lobbyId) }
        logger.i("VM init")
        launchWithHandler {
            subscribeToLobby()
        }
        logger.i("VM initialized successfully")
    }

    private suspend fun subscribeToLobby(){
        logger.i("subscribe to Lobby")
        setState { copy(isLoading=true) }
        usecase
            .subscribeToLobby(state.value.lobbyId)
            .collect { event->
                logger.i("Event received: $event")

                when(event){
                    is LobbyEvent.Connected -> {
                        logger.i("Lobby events Connected")
                    }
                    is LobbyEvent.CurrentLobby -> {
                        logger.i("Lobby events lobby received")

                        val name = usecase.getUsername()
                        logger.i("has joined? -> ${state.value.isJoined}; players -> ${state.value.lobby?.players}; username -> ${name}")
                        setState { copy(lobby=event.lobby, isLoading = false, isJoined=event.lobby.players.contains(name), username = name) }
                    }
                    is LobbyEvent.Error -> {
                        logger.i("Lobby events error -> ${event.message}")
                        val name = usecase.getUsername()
                        setState { copy(error=event.message, isLoading = false, isJoined=lobby?.players?.contains(name) ?: false) }
                    }
                    LobbyEvent.KeepAlive -> {
                        logger.i("Lobby events KeepAlive")
                        setState { copy(isLoading = false) }
                    }
                    is LobbyEvent.PlayerJoined ->{
                        logger.i("Lobby events PlayerJoined ${event.username}")
                        val currentPlayers = state.value.lobby?.players ?: emptyList()
                        setState {
                            copy(
                                isLoading = false,
                                lobby=lobby?.copy(
                                    players= currentPlayers + event.username
                                ),
                            )
                        }
                        logger.i("After JOin state: ${state.value}")
                    }
                    is LobbyEvent.PlayerLeft ->{
                        logger.i("Lobby events PlayerLeft ${event.username}")
                        val currentPlayers = state.value.lobby?.players ?: emptyList()
                        setState {
                            copy(
                                isLoading = false,
                                lobby=lobby?.copy(
                                    players= currentPlayers - event.username
                                )
                            )
                        }
                        if(state.value.lobby?.players?.isEmpty() == true || !event.isActive)
                            sendEffect(LobbyNavigation.ToBrowse)
                    }

                    is LobbyEvent.MatchInit -> {
                        logger.i("Lobby event match received -> id=${event.matchId}")
                        setState { copy(isLoading = false) }
                        sendEffect(LobbyNavigation.ToMatch(event.matchId))
                    }
                }

            }

    }


    fun onBackRequest(){
        logger.i("Back Requested -> Navigate to Browse Activity")
        setState { copy(isLoading = true) }
        launchWithHandler(
            onError = { e ->
                logger.e("Leave Lobby crashed", e)
                setState { copy(isLoading = false, error = e.message) }
            }
        ) {
            usecase
                .leaveLobby(state.value.lobbyId)
                .fold(
                    onSuccess = { lobbyId ->
                        logger.i("Lobby Left successfully: $lobbyId")
                        setState { copy(isLoading = false, isJoined =false, lobby=null,lobbyId="") }
                        sendEffect(LobbyNavigation.ToBrowse )
                    },
                    onFailure = { e ->
                        logger.w("Leave lobby failed: ${e.message}")
                        setState { copy(isLoading = false, error = e.message) }
                    }
                )
        }
    }

    fun onStartRequest(){
        //sendEffect(LobbyNavigation.ToMatch)
        logger.i("Start Match Requested -> Atempting to start match")
        setState { copy(isLoading = true) }
        launchWithHandler(
            onError = { e ->
                logger.e("Start Match crashed", e)
                setState { copy(isLoading = false, error = e.message) }
            }
        ) {
            usecase
                .startMatch(state.value.lobbyId)
                .fold(
                    onSuccess = { lobbyId ->
                        logger.i("Started match successfully: $lobbyId")
                        setState { copy(isLoading = false) }
                    },
                    onFailure = { e ->
                        logger.w("Start Match failed: ${e.message}")
                        setState { copy(isLoading = false, error = e.message) }
                    }
                )
        }
    }

    fun onJoinRequest(){
        if (state.value.isJoined) {
            logger.w("Player Already Joined")
            return
        }
        if (state.value.lobby?.players?.size == state.value.lobby?.maxPlayers) {
            logger.w("Lobby is Full")
            return
        }
        logger.i("Join Lobby Requested -> Atempting to join user to the lobby")
        setState { copy(isLoading = true) }
        launchWithHandler(
            onError = { e ->
                logger.e("Join Lobby crashed", e)
                setState { copy(isLoading = false, error = e.message) }
            }
        ) {
            usecase
                .joinLobby(state.value.lobbyId)
                .fold(
                    onSuccess = { lobbyId ->
                        logger.i("Lobby joined successfully: $lobbyId")
                        setState { copy(
                            isLoading = false,
                            isJoined=true,
                            ) }
                    },
                    onFailure = { e ->
                        logger.w("Join lobby failed: ${e.message}")
                        setState { copy(isLoading = false, error = e.message) }
                        sendEffect(LobbyNavigation.ToBrowse )
                    }
                )
        }
    }

}