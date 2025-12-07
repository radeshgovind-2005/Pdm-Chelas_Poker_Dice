package isel.pdm.pokerdice.domain.match

data class Match(
    val lobbyId: String,
    val matchId: String,
    val status: String,
    val roundNr: Int?,
    val turn: String?
)