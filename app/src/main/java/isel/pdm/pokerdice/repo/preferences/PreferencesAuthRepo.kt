package isel.pdm.pokerdice.repo.preferences

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map



/**
 * Implementation of AuthInfoRepo that uses SharedPreferences to store authentication information.
 */
class PreferencesAuthRepo(private val store: SharedPreferences) : AuthRepository {

    private val usernameKey = "username"
    private val authTokenKey = "auth_token"

    // We use a StateFlow to emit updates immediately to the UI
    private val _authFlow = MutableStateFlow<AuthInfo?>(getCurrentAuthInfo())
    override val authInfo: Flow<AuthInfo?> = _authFlow.asStateFlow()

    override suspend fun saveAuthInfo(authInfo: AuthInfo) {
        store.edit()
            .putString(usernameKey, authInfo.userName.value)
            .putString(authTokenKey, authInfo.authToken)
            .apply() // EncryptedSharedPreferences handles this securely

        // Emit the new state to the app
        _authFlow.value = authInfo
    }

    override suspend fun getAuthInfo(): AuthInfo? = getCurrentAuthInfo()

    override suspend fun clearAuthInfo() {
        store.edit().clear().apply()
        _authFlow.value = null
    }

    private fun getCurrentAuthInfo(): AuthInfo? {
        val username = store.getString(usernameKey, null) ?: return null
        val token = store.getString(authTokenKey, null) ?: return null

        return Name.create(username).getOrNull()?.let { validName ->
            AuthInfo(userName = validName, authToken = token)
        }
    }
}