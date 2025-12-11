package isel.pdm.pokerdice.ui.viewmodels.match


import isel.pdm.pokerdice.domain.Hand
import isel.pdm.pokerdice.domain.Round
import isel.pdm.pokerdice.domain.match.Game
import isel.pdm.pokerdice.domain.match.MatchPlayers

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