package isel.pdm.pokerdice.domain.model.lobby

data class BrowseLobby(
    val id: String,
    val name: String,
    val rounds: Int,
    val hostName: String,
)