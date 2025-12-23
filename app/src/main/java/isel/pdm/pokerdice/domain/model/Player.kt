package isel.pdm.pokerdice.domain.model

data class Player (
    val name: String,
    val balance: Int,
    val hasPlayed: Boolean=false
)