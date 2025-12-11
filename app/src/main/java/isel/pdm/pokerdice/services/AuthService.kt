package isel.pdm.pokerdice.services

import isel.pdm.pokerdice.domain.types.Password
import isel.pdm.pokerdice.domain.types.Username
import isel.pdm.pokerdice.domain.user.SessionInfo
import isel.pdm.pokerdice.domain.user.UserStats

interface AuthService {
    suspend fun login(username: Username, pass: Password): String?
    suspend fun sessionCheck(token:String): SessionInfo
    suspend fun logout(token: String)
    suspend fun getUserStats(token: String): UserStats
}