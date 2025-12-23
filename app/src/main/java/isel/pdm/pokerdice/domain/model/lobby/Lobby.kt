package isel.pdm.pokerdice.domain.model.lobby

data class Lobby(
    val id: String,
    val name: String,
    val description: String,
    val host: String,
    var state: String,
    val maxRounds: Int,
    val maxPlayers: Int,
    val ante: Int,
    val initialBalance: Int,
    val players: List<String>
)