package isel.pdm.pokerdice.domain.services

import isel.pdm.pokerdice.domain.rules.Password
import isel.pdm.pokerdice.domain.rules.Username
import isel.pdm.pokerdice.domain.model.user.SessionInfo
import isel.pdm.pokerdice.domain.model.user.UserStats

interface AuthService {
    suspend fun login(username: Username, pass: Password): String?
    suspend fun sessionCheck(token:String): SessionInfo
    suspend fun logout(token: String)
    suspend fun getUserStats(token: String): UserStats
}