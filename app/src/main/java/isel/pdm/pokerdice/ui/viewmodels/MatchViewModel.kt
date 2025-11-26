package isel.pdm.pokerdice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.GameLog
import isel.pdm.pokerdice.domain.Match
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.services.MatchServices
import isel.pdm.pokerdice.services.fake.FakeMatchService

class MatchViewModel(
    private val services: MatchServices = FakeMatchService()
) : BaseViewModel<MatchViewModel.State>() {

    companion object {
        fun getFactory(gameService: MatchServices) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
                    MatchViewModel(services = gameService) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override val initialState: State = State.Idle

    fun loadMatch(match: Match){
        updateState(State.MatchInit(match))
    }
    fun firstStart(){
        updateState(State.Idle)
    }

    fun startMatch(lobby: Lobby): Match?{
        val e=Exception("Match not created successfully!")
        val e1=Exception("Match domain not created successfully!")

        var match: Match? = null
        launch(onError={ State.Error(it)}){
            updateState(State.MatchCreating)
            Match
                .create(lobby)
                .fold(
                    onSuccess={ newMatch->
                        services
                            .insertMatch(newMatch)
                            .collect{ data->
                                data
                                    ?.let{match=it;updateState(State.MatchCreated(match))}
                                    ?: updateState(State.Error(e))
                            }
                    },
                    onFailure={updateState(State.Error(e1))}
                )
        }
        return match
    }


    fun matchBegins(){
        if(state.value !is State.MatchInit) GameLog.logDebug("Match View Model not initialized!")
        val s = state.value as State.MatchInit
        updateState(State.MatchRunning(s.match))
    }

    fun rollDices(){
        launch(onError = {State.Error(it)}){
            //services.rollDices()
        }
    }
    sealed interface State {
        data object Idle : State
        data class Error(val e: Throwable) : State
        data object MatchCreating: State
        data class MatchCreated(val match: Match): State
        data object MatchLoading: State
        data class MatchInit(val match: Match): State
        data class MatchRunning(var match: Match): State

    }
}