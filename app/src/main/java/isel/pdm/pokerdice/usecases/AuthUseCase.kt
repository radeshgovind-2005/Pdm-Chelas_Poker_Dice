package isel.pdm.pokerdice.usecases

import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.repo.AuthRepository
import isel.pdm.pokerdice.services.AuthService
import isel.pdm.pokerdice.domain.UserCredentials


/**
 * Use case class that encapsulates the login business logic.
 */
class AuthUseCase(
    private val authService: AuthService,
    private val authRepo: AuthRepository
) {
    suspend fun login(credentials: UserCredentials): User {
        val authToken = authService.login(credentials)
        val name = Name.create(credentials.username).getOrThrow()
        val authInfo = AuthInfo(userName = name, authToken = authToken)
        authRepo.saveAuthInfo(authInfo)
        return User(credentials,authInfo)
    }

    suspend fun getLoggedUser(): User? {
        val authInfo = authRepo.getAuthInfo() ?: return null
        val credentials = UserCredentials(
            username = authInfo.userName.value,
            password =  "1Ola" // Password is not stored; forcing a default value
        )
        return User(credentials, authInfo)
    }
}