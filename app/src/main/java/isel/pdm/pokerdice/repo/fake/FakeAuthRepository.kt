package isel.pdm.pokerdice.repo.fake

import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * A fake implementation of AuthInfoRepo for testing and development purposes.
 */
class FakeAuthInfoRepo : AuthRepository {
    private var storedAuthInfo: AuthInfo? = null
    private val _authInfo = MutableStateFlow(storedAuthInfo)

    override val authInfo: Flow<AuthInfo?>
        get() =  _authInfo

    override suspend fun saveAuthInfo(authInfo: AuthInfo) {
        storedAuthInfo = authInfo
        _authInfo.value = authInfo
    }

    override suspend fun getAuthInfo(): AuthInfo? {
        return storedAuthInfo
    }

    override suspend fun clearAuthInfo() {
        storedAuthInfo = null
        _authInfo.value = null
    }
}