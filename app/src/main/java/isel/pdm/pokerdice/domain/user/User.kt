package isel.pdm.pokerdice.domain.user

import isel.pdm.pokerdice.domain.types.Username

data class User(val authInfo: AuthInfo)

data class AuthInfo(val username: Username, val authToken: String? = null)