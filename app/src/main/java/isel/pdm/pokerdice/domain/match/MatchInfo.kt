package isel.pdm.pokerdice.domain.match

data class MatchInfo(
    val matchid: String,
    val lobbyId: String,
    val status: String,
    val totalRounds: Int,
    val ante: Int,
    val currentRound: Int?,
    val turnName: String?,
    val winner: Int?,
)