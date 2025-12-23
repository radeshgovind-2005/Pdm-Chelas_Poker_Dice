package isel.pdm.pokerdice.domain.model.match

data class Game(
    val username: String?,
    val match: MatchContent,
    val lobby: LobbyContent,
    val round: Round? = null,
    val msg: String?,
)
data class MatchContent(
    val matchId: String,
    val status: String,
    val isStarted: Boolean,
    val isCompleted: Boolean
)
data class LobbyContent(
    val lobbyId: String,
    val name: String,
    val ante: Int,
)
data class Round(
    val totalRounds: Int,
    val currRound: Int,
    val roundBet: Int,
    val players: List<MatchPlayers>,
)
data class MatchPlayers(
    val name: String,
    val hand: String,
    val rank: String,
    val balance: Int,
    val state: String,
    val rerollsLeft: Int
)