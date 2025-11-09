package isel.pdm.pokerdice.repo.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import isel.pdm.pokerdice.domain.AuthInfo
import isel.pdm.pokerdice.domain.values.Name
import isel.pdm.pokerdice.repo.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map



/**
 * Implementation of AuthInfoRepo that uses SharedPreferences to store authentication information.
 */
class PreferencesAuthRepo(private val store: DataStore<Preferences>) : AuthRepository {

    private val usernameKey: Preferences.Key<String> = stringPreferencesKey(name = "username")
    private val authTokenKey: Preferences.Key<String> = stringPreferencesKey(name = "auth_token")

    override val authInfo: Flow<AuthInfo?> =
        store
            .data
            .map { preferences ->
                preferences.toAuthInfo()
            }


    override suspend fun saveAuthInfo(authInfo: AuthInfo) {
        store.edit { preferences ->
            preferences[usernameKey] = authInfo.userName.value
            preferences[authTokenKey] =  authInfo.authToken
        }
    }

    override suspend fun getAuthInfo(): AuthInfo? {
        val preferences: Preferences = store.data.last()
        return preferences.toAuthInfo()
    }

    override suspend fun clearAuthInfo() {
        store.edit { it.clear() }
    }

    fun Preferences.toAuthInfo(): AuthInfo? =
        this[usernameKey]?.let {
            val token = this[authTokenKey] ?: return null
            Name
                .create(it)
                .fold(
                    onSuccess = {AuthInfo(userName = it, authToken = token)},
                    onFailure = {null}
                )
        }
}