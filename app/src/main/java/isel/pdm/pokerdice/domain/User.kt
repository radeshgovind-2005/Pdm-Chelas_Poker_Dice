package isel.pdm.pokerdice.domain

import java.util.UUID

data class User (
    val userCredentials: UserCredentials,
    val authInfo: AuthInfo
)

typealias Users = MutableList<User>