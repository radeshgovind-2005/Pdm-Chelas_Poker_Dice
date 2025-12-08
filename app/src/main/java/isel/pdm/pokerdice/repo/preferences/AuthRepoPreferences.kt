package isel.pdm.pokerdice.repo.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import isel.pdm.pokerdice.domain.user.AuthInfo
import isel.pdm.pokerdice.domain.types.Username
import isel.pdm.pokerdice.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepoPreferences(private val prefs: SharedPreferences): AuthRepository {
    private companion object{
        const val KEY_USERNAME = "auth_username"
        const val KEY_TOKEN = "auth_token"
    }
    private val _authInfo = MutableStateFlow(readFromStorage())
    override val authInfo: Flow<AuthInfo?> = _authInfo.asStateFlow()

    override suspend fun saveAuthInfo(authInfo: AuthInfo) {
        _authInfo.value = authInfo
        prefs.edit {
            putString(KEY_USERNAME, authInfo.username.value)
            putString(KEY_TOKEN, authInfo.authToken)
        }
    }

    override suspend fun getAuthInfo(): AuthInfo? {
        return readFromStorage()
    }

    override suspend fun clearAuthInfo() {
        _authInfo.value = null
        prefs.edit {
            remove(KEY_USERNAME)
            remove(KEY_TOKEN)
        }
    }

    private fun readFromStorage(): AuthInfo? {
        val username = prefs.getString(KEY_USERNAME, null)
        val token = prefs.getString(KEY_TOKEN, null)

        return if (username != null && token != null) {
            AuthInfo(Username(username), token)
        } else {
            null
        }
    }
}