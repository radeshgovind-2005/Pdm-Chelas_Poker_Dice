package isel.pdm.pokerdice.domain.model.user

import isel.pdm.pokerdice.domain.rules.Username

data class User(val authInfo: AuthInfo)

data class AuthInfo(val username: Username, val authToken: String? = null)

data class SessionInfo(
    val id: Int,
    val username: String,
    val lobbyId: String?,
    val matchId: String?
)