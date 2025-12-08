package isel.pdm.pokerdice.domain

data class Player (
    val name: String,
    val balance: Int,
    val hand: Dices= Dices(),
    val hasPlayed: Boolean=false
)