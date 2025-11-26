package isel.pdm.pokerdice

import android.app.Application
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import isel.pdm.pokerdice.services.fake.FakeLobbyService
import isel.pdm.pokerdice.services.fake.FakeAuthService
import isel.pdm.pokerdice.repo.preferences.PreferencesAuthRepo
import isel.pdm.pokerdice.services.fake.FakeMatchService
import isel.pdm.pokerdice.ui.notifications.NotificationSource
import kotlin.getValue
import isel.pdm.pokerdice.usecases.AuthUseCase

class HostApplication: Application() {

    lateinit var notificationSource: NotificationSource
        private set

    private val masterKey by lazy {
        MasterKey.Builder(this)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    //Create EncryptedSharedPreferences
    // This creates a file named "secure_poker_prefs.xml" that is automatically encrypted.
    private val securePreferences by lazy {
        EncryptedSharedPreferences.create(
            this,
            "secure_poker_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    val lobbyService by lazy { FakeLobbyService() }
    val authService by lazy { FakeAuthService() }
    val authRepo by lazy { PreferencesAuthRepo(securePreferences) }
    val authUseCase by lazy { AuthUseCase(authService,authRepo) }
    val gameService by lazy { FakeMatchService() }

    override fun onCreate() {
        super.onCreate()
        notificationSource = NotificationSource(this)
        notificationSource.createChannel()
    }
}