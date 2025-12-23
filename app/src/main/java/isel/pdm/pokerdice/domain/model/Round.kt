package isel.pdm.pokerdice.domain.model

data class Round (
    val roundCounter: Int=0,
    val players: List<Player>,
    val prize: Int,
    val turn: String?,
    val isRolling: Boolean,
    val rerollCounter: Int
)