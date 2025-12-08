package isel.pdm.pokerdice.domain.user


data class UserState(
    val state: String, // "waiting" ou "playing"
    val contextId: String // O ID do lobby ou da match
)