package isel.pdm.pokerdice.repo

import isel.pdm.pokerdice.domain.user.AuthInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val authInfo: Flow<AuthInfo?>

    /**
     * Saves the provided authentication information.
     * @param authInfo The authentication information to be saved.
     */
    suspend fun saveAuthInfo(authInfo: AuthInfo)

    /**
     * Retrieves the stored authentication information.
     * @return The stored authentication information, or null if none is stored.
     */
    suspend fun getAuthInfo(): AuthInfo?

    /**
     * Clears the stored authentication information.
     */
    suspend fun clearAuthInfo()
}