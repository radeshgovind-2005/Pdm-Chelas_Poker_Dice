package isel.pdm.pokerdice.app

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import isel.pdm.pokerdice.data.repo.AuthRepoPreferences
import isel.pdm.pokerdice.data.services.http.HttpAuthService
import isel.pdm.pokerdice.data.services.http.HttpLobbyService
import isel.pdm.pokerdice.data.services.http.HttpMatchService
import isel.pdm.pokerdice.domain.usecases.AuthUseCase
import isel.pdm.pokerdice.domain.usecases.BrowseUseCase
import isel.pdm.pokerdice.domain.usecases.CreateUseCase
import isel.pdm.pokerdice.domain.usecases.LobbyUseCase
import isel.pdm.pokerdice.domain.usecases.MainUseCase
import isel.pdm.pokerdice.domain.usecases.MatchUseCase
import isel.pdm.pokerdice.domain.usecases.ProfileUseCase
import okhttp3.OkHttpClient


class AppContainer(context: Context) {

    @Suppress("DEPRECATION")
    private val masterKey by lazy {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyGenParameterSpec(spec)
            .build()
    }

    @Suppress("DEPRECATION")
    private val securePreferences: SharedPreferences by lazy {
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
    val authService by lazy { HttpAuthService(client, gson) }
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