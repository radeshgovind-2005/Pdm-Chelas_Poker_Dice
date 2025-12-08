package isel.pdm.pokerdice.domain.lobby

data class BrowseLobby(
    val id: String,
    val name: String,
    val rounds: Int,
    val hostName: String,
)