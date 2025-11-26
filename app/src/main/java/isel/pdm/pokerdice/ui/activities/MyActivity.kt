package isel.pdm.pokerdice.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import kotlin.getValue

abstract class MyActivity: NavActivity() {
    val lobbyViewModel: LobbyViewModel by viewModels {
        val app = (application as HostApplication)
        LobbyViewModel.getFactory(
            app.lobbyService,
            app.notificationSource
        )
    }

    val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.getFactory((application as HostApplication).authUseCase)
    }

    val gameViewModel: MatchViewModel by viewModels {
        MatchViewModel.getFactory((application as HostApplication).gameService)
    }

    fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Request the permission
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }
}