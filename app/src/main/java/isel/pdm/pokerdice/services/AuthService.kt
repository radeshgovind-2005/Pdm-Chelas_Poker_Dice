package isel.pdm.pokerdice.services

import isel.pdm.pokerdice.domain.UserCredentials

/**
 * Interface representing the Authentication service.
 */
interface AuthService {
    /**
     * Logs in a user with the provided credentials.
     * @param credentials The user's credentials.
     * @return A string representing the authentication token to be used upon successful login.
     * @throws InvalidCredentialsException if the login fails.
     */
    suspend fun login(credentials: UserCredentials): String

    /**
     * Logs out a user.
     */
    suspend fun logout(username: String)
}