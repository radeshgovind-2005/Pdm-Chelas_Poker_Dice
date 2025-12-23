package isel.pdm.pokerdice.ui.screens.match

import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.screens.match.otherViews.AnounceRoundWinner
import isel.pdm.pokerdice.ui.screens.match.otherViews.FetchingView
import isel.pdm.pokerdice.ui.screens.match.otherViews.PlayView
import isel.pdm.pokerdice.ui.screens.match.otherViews.WaitingRoundView
import isel.pdm.pokerdice.ui.viewmodels.match.MatchState

@Composable
fun MatchScreen(
    state: MatchState,
    onStartRound: () -> Unit,
    onClickDice: (Int) -> Unit,
    onClickPlay: () -> Unit,
    onClickNext: () -> Unit
) {
    //se game for nulo
    val fetchingGame = state.game == null
    //se ronda for nula e existem rounds por realizar
    val noActiveRounds = state.game?.round == null && state.game?.match?.isCompleted == false
    //se a ronda estiver a ocorrer
    val onRound = state.game?.round != null && state.game?.match?.isCompleted == false
    val onRoundComplete = state.game?.msg != null
    when{
        fetchingGame ->  FetchingView()
        noActiveRounds -> WaitingRoundView(state.game,onStartRound)
        onRoundComplete -> AnounceRoundWinner(game=state.game,onClickNext)
        onRound -> PlayView(state,onClickDice,onClickPlay)
    }
}