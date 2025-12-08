package isel.pdm.pokerdice.ui.viewmodels.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.Hand
import isel.pdm.pokerdice.services.events.MatchEvents
import isel.pdm.pokerdice.ui.viewmodels.BaseViewModel
import isel.pdm.pokerdice.ui.viewmodels.usecases.MatchUseCase
import kotlinx.coroutines.flow.retry

class MatchViewModel (
    private val usecase: MatchUseCase
) : BaseViewModel<MatchState, MatchNavigation>(MatchState()){

    private val logger = AppLog(this::class.java.simpleName)

    companion object {
        fun getFactory(usecase: MatchUseCase) = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
                    MatchViewModel(usecase = usecase) as T
                } else throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun inititializeVM(matchId: String){
        setState { copy(matchId=matchId, isLoading=true) }
        logger.i("VM init")
        launchWithHandler(
            onError = { e ->
                logger.e("Match Events crashed", e)
                setState { copy(isLoading = false, error = e.message) }
            }
        ) {

           val username = usecase.getUsername().getOrNull()
            usecase
                .subscribeToMatch(matchId)
                .retry { cause ->
                    logger.w("SSE connection lost: ${cause.message}. Retrying...")
                    cause is Exception
                }
                .collect{ event ->
                    logger.i("Event received: $event")
                    when(event) {
                        is MatchEvents.Subscribed -> {
                            logger.i("Subscribed Successfully")
                            val eventRound = event.game.round
                            val playerTurn = eventRound?.players?.firstOrNull{ it.state=="turn"}
                            setState {
                                copy(
                                    game=event.game.copy(username=username),
                                    isLoading = false,
                                    currTurn = playerTurn,
                                    )
                            }
                        }
                        is MatchEvents.RoundInit -> {
                            logger.i("Round Init")
                            val playerTurn = event.game.round?.players?.firstOrNull{ it.state=="turn"}
                            setState {
                                copy(
                                    currTurn = playerTurn,
                                    game= game?.copy( round=event.game.round) ?: event.game,
                                    currHand= Hand(),
                                    isLoading = false
                                )
                            }
                        }

                        is MatchEvents.HoldAll -> {
                            logger.i("Round HoldAll")
                            val eventRound = event.game.round
                            val playerTurn = eventRound?.players?.firstOrNull{ it.state=="turn"}
                            val updatedHand = if(playerTurn != null) {
                                state.value.currHand.updateHand(playerTurn.hand)
                            } else {
                                state.value.currHand
                            }
                            setState {
                                copy(
                                    game= game?.copy( round=eventRound,msg=null) ?: event.game,
                                    currTurn = playerTurn,
                                    currHand = updatedHand,
                                    isLoading = false
                                )
                            }
                        }
                        is MatchEvents.ReRoll -> {
                            logger.i("Round ReRoll")
                            val eventRound = event.game.round
                            val playerTurn = eventRound?.players?.firstOrNull{ it.state=="turn"}
                            val updatedHand = if(playerTurn != null) {
                                // Usar a função que retorna a nova instância
                                state.value.currHand.updateHand(playerTurn.hand)
                            } else {
                                state.value.currHand
                            }
                            setState {
                                copy(
                                    game= game?.copy( round=eventRound,msg=null) ?: event.game,
                                    currTurn = playerTurn,
                                    currHand = updatedHand,
                                    isLoading = false
                                )
                            }
                        }
                        is MatchEvents.RollAll -> {
                            logger.i("Round RollAll")
                            val eventRound = event.game.round
                            val playerTurn = eventRound?.players?.firstOrNull{ it.state=="turn"}
                            val updatedHand = if(playerTurn != null) {
                                // Usar a função que retorna a nova instância
                                state.value.currHand.updateHand(playerTurn.hand)
                            } else {
                                state.value.currHand
                            }
                            setState {
                                copy(
                                    game= game?.copy( round=eventRound,msg=null) ?: event.game,
                                    currTurn = playerTurn,
                                    currHand = updatedHand,
                                    isLoading = false
                                )
                            }
                        }
                        is MatchEvents.RoundComplete -> {
                            logger.i("Round Complete -> ${event.game.msg}")
                            setState {
                                copy(
                                    game= game?.copy(
                                        match = event.game.match,
                                        round = event.game.round,
                                        msg = event.game.msg
                                    ) ?: event.game,
                                    isLoading = false
                                )
                            }
                        }

                        is MatchEvents.Connected -> {
                            logger.i("Match events Connected")
                            setState { copy(isLoading = false) }
                        }
                        is MatchEvents.Error -> {
                            logger.i("Match events Error")
                            setState { copy(isLoading = false, error = event.message) }
                        }
                        MatchEvents.KeepAlive -> {
                            logger.i("Match events KeepAlive")
                            setState { copy(isLoading = false) }
                        }
                    }
                }
        }
        logger.i("VM initialized successfully")
    }


    fun onMatchEnd(){
        val lobbyId = state.value.game?.lobby?.lobbyId
        logger.i("matchEnded changing activity to lobby if possible -> lobbyId is $lobbyId")
        if(lobbyId == null)
             sendEffect(MatchNavigation.ToTitle)
        else
            sendEffect(MatchNavigation.ToLobby(lobbyId))

    }

    fun onClickDice(idx: Int){
        if(state.value.currTurn?.name != state.value.game?.username) return
        logger.i("attempting to select dice $idx")
        val newHand = state.value.currHand.toggleSelection(idx)
        setState { copy(currHand = newHand) }
        logger.i("Hand updated: ${newHand.value}")
    }

    fun onClickNext(){
        val currRound = state.value.game?.round?.currRound
        val maxRound = state.value.game?.round?.totalRounds
        val lobbyId = state.value.game?.lobby?.lobbyId
        val match = state.value.game?.match

        when{
            lobbyId == null -> logger.w("lobby Id == null")
            match == null -> logger.w("match == null")
            match.isCompleted -> sendEffect(MatchNavigation.ToLobby(lobbyId))
            maxRound == null-> logger.w("max round == null")
            currRound == null-> logger.w("curr round == null")
            currRound <= maxRound && state?.value?.game?.round?.players?.none{it.state == "not_in_game"} == true -> {
                setState { copy(game=game?.copy(msg=null)) }
                onStartRound()
            }
        }
    }

    fun onClickPlay(){
        logger.i("attempting to play")
        launchWithHandler(
            onError = { e ->
                logger.e("Play Match crashed", e)
                setState { copy(isLoading = false, error = e.message) }
            }
        ) {
            val matchId = state.value.game?.match?.matchId ?: throw Exception("Match Id is null")
            val currentUser = state.value.game?.username
            val players = state.value.round?.players ?: emptyList()
            val areOthersFinished = players
                .filter { it.name != currentUser }
                .all { player ->
                    player.hasPlayed==true
                }
            when{
                state.value.currTurn?.rerollsLeft == 3 -> {
                    //roll all
                    usecase.rollAllDices(matchId)
                }
                state.value.currTurn?.rerollsLeft == 0 ||
                     state.value.currHand.value.all{it.isSelected}-> {
                    //hold all
                    usecase
                        .holdDices(matchId)
                        .onSuccess {
                            if (areOthersFinished) {
                                logger.i("I am the last player. Triggering completeRound.")
                                usecase.completeRound(matchId)
                            }
                        }
                }

                else ->{
                    //roll unsulected
                    val unselectedDices = state.value.currHand.value.mapIndexed { idx, elem ->
                        if(elem.isSelected)
                            idx + 1
                        else -1
                    }.filter{it >= 0}
                    usecase.rollDices(matchId, unselectedDices)
                }
            }
        }
    }

    fun onStartRound(){
        logger.i("attempting to start round")
        launchWithHandler(
            onError = { e ->
                logger.e("Start round crashed", e)
                setState { copy(isLoading = false, error = e.message) }
            }
        ) {
            usecase
                .startRound(state.value.matchId)
                .fold(
                    onSuccess = {
                        logger.i("started round successfully: matchId is ${state.value.matchId}")
                    },
                    onFailure = { e ->
                        logger.i("started round failed: ${e.message} ")
                        setState { copy(error=e.message) }
                    }
                )
        }
    }

}