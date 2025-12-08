package isel.pdm.pokerdice.ui.viewmodels.usecases

import isel.pdm.pokerdice.domain.user.AuthInfo
import isel.pdm.pokerdice.domain.user.User
import isel.pdm.pokerdice.domain.types.Password
import isel.pdm.pokerdice.domain.types.Username
import isel.pdm.pokerdice.repo.AuthRepository
import isel.pdm.pokerdice.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InvalidCredentialsException : Exception("Invalid username or password")
class NetworkException(message: String) : Exception(message)

class AuthUseCase(
    private val authService: AuthService,
    private val authRepo: AuthRepository
) {
    suspend fun performLogin(username: Username, pass: Password): Result<User> =
        withContext(Dispatchers.IO) {
            runCatching {
                val authToken = try {
                    authService.login(username, pass)
                } catch (e: Exception) {
                    throw NetworkException("Login service unavailable: ${e.message}")
                }

                if (authToken == null) throw InvalidCredentialsException()

                val authInfo = AuthInfo(username, authToken)
                authRepo.saveAuthInfo(authInfo)

                User(authInfo)
            }
        }

}