package isel.pdm.pokerdice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.services.GameServices
import isel.pdm.pokerdice.services.fake.FakeGameServices

class GameViewModel(
    private val gameService: GameServices = FakeGameServices()
) : BaseViewModel<GameViewModel.State>() {

    companion object {
        fun getFactory(gameService: GameServices) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                    GameViewModel(gameService = gameService) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override val initialState: State = State.Idle

    fun initializeMatch(){
        updateState(State.MatchInit)
    }
    fun initializeRound(){
        updateState(State.PlayingInRound())
    }

    sealed interface State {
        data object Idle : State
        data class Error(val e: Throwable) : State
        data object MatchInit: State

        data class PlayingInRound(val Nround: Int = 0,var isRolling: Boolean=false): State
    }
}