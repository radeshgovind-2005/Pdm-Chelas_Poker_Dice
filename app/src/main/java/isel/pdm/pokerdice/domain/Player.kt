package isel.pdm.pokerdice.domain

data class Player (
    val name: String,
    val balance: Int,
    val hasPlayed: Boolean=false
)