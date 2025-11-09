package isel.pdm.pokerdice.services.fake

import isel.pdm.pokerdice.domain.InvalidCredentialsException
import isel.pdm.pokerdice.domain.UserCredentials
import isel.pdm.pokerdice.services.AuthService

/**
 * A fake implementation of the LoginService for testing purposes.
 */
class FakeAuthService : AuthService {

    data class PassAndToken(val pass: String, var token: String? = null)
    private val users = mutableMapOf<String,PassAndToken?>()


    override suspend fun login(credentials: UserCredentials): String {
        val token = "fake-token-isel-leic-pdm"
        users[credentials.username]?.let{ pt ->
            if(pt.pass != credentials.password)
                throw InvalidCredentialsException()
            pt.token = token
        } ?: {
            val pt = PassAndToken(credentials.password,token)
            users.put(credentials.username, pt)
        }
        return token
    }

    override suspend fun logout(username: String) {
        users[username]?.token = null
    }
}