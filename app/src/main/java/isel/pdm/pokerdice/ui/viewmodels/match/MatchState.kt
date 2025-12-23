package isel.pdm.pokerdice.ui.viewmodels.match


import isel.pdm.pokerdice.domain.model.Hand
import isel.pdm.pokerdice.domain.model.Round
import isel.pdm.pokerdice.domain.model.match.Game
import isel.pdm.pokerdice.domain.model.match.MatchPlayers

data class MatchState(
    val matchId: String="",
    val round: Round? = null,
    val username: String = "",
    val game: Game? = null,
    val currHand: Hand = Hand(),
    val currTurn: MatchPlayers? = null,
    val error: String? = null,
    val isLoading: Boolean=false,
    )