package isel.pdm.pokerdice.app

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import isel.pdm.pokerdice.repo.preferences.AuthRepoPreferences
import isel.pdm.pokerdice.services.http.HttpAuthService
import isel.pdm.pokerdice.services.http.HttpLobbyService
import isel.pdm.pokerdice.services.http.HttpMatchService
import isel.pdm.pokerdice.ui.viewmodels.usecases.AuthUseCase
import isel.pdm.pokerdice.ui.viewmodels.usecases.BrowseUseCase
import isel.pdm.pokerdice.ui.viewmodels.usecases.CreateUseCase
import isel.pdm.pokerdice.ui.viewmodels.usecases.LobbyUseCase
import isel.pdm.pokerdice.ui.viewmodels.usecases.MainUseCase
import isel.pdm.pokerdice.ui.viewmodels.usecases.MatchUseCase
import isel.pdm.pokerdice.ui.viewmodels.usecases.ProfileUseCase
import okhttp3.OkHttpClient

class AppContainer(context: Context) {

    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val securePreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "secure_poker_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val client by lazy { OkHttpClient() }
    private val gson by lazy { Gson() }

    //AUTH & REPO
    val authService by lazy { HttpAuthService(client,gson) }
    val lobbyService by lazy { HttpLobbyService(client, gson) }
    val matchService by lazy { HttpMatchService(client, gson) }
    val authRepo by lazy { AuthRepoPreferences(securePreferences) }

    //USE CASES
    val authUseCase by lazy { AuthUseCase(authService, authRepo) }
    val mainUseCase by lazy { MainUseCase(authRepo, authService, lobbyService) }
    val profileUseCase by lazy { ProfileUseCase(authService,authRepo) }
    val browseUseCase by lazy { BrowseUseCase(lobbyService) }
    val createUseCase by lazy { CreateUseCase(lobbyService,authRepo) }
    val lobbyUseCase by lazy { LobbyUseCase(matchService,lobbyService, authRepo) }
    val matchUseCase by lazy { MatchUseCase(matchService, authRepo) }
}