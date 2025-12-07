package isel.pdm.pokerdice.services

import isel.pdm.pokerdice.domain.types.Password
import isel.pdm.pokerdice.domain.types.Username

interface AuthService {
    suspend fun login(username: Username, pass: Password): String?
    suspend fun sessionCheck(token:String): String
    suspend fun logout(token: String)
}