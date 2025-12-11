package isel.pdm.pokerdice.services.fake

import isel.pdm.pokerdice.domain.types.Password
import isel.pdm.pokerdice.domain.types.Username
import isel.pdm.pokerdice.domain.user.SessionInfo
import isel.pdm.pokerdice.domain.user.UserStats
import isel.pdm.pokerdice.services.AuthService

class FakeAuthService: AuthService {
    override suspend fun login(
        username: Username,
        pass: Password
    ): String? {
        TODO("Not yet implemented")
    }

    override suspend fun sessionCheck(token: String): SessionInfo {
        TODO("Not yet implemented")
    }

    override suspend fun logout(token: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserStats(token: String): UserStats {
        TODO("Not yet implemented")
    }

}