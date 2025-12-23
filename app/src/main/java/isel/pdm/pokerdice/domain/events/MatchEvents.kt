package isel.pdm.pokerdice.domain.events

import isel.pdm.pokerdice.domain.model.match.Game

sealed class MatchEvents {
    data class Subscribed(val game: Game): MatchEvents()
    data class RoundInit(val game: Game): MatchEvents()
    data class RoundComplete(val game: Game): MatchEvents()
    data class RollAll(val game: Game): MatchEvents()
    data class ReRoll(val game: Game): MatchEvents()
    data class HoldAll(val game: Game): MatchEvents()
    data class Error(val message: String) : MatchEvents()

    data class Connected(val message: String) : MatchEvents()
    data object KeepAlive :MatchEvents()
}