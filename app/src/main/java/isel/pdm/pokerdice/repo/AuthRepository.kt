package isel.pdm.pokerdice.repo

import isel.pdm.pokerdice.domain.AuthInfo
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing authentication information.
 */
interface AuthRepository {
    /**
     * A Flow that emits the current authentication information, or null if none is stored.
     */
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