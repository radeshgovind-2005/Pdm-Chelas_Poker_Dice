package isel.pdm.pokerdice.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import isel.pdm.pokerdice.HostApplication
import isel.pdm.pokerdice.LobbyLog
import isel.pdm.pokerdice.SessionLog
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.ui.activities.screens.session.SessionExpiredScreen
import isel.pdm.pokerdice.ui.components.progressindicator.DefaultCircularProgressIndicator
import isel.pdm.pokerdice.ui.navigation.NavActivity
import isel.pdm.pokerdice.ui.navigation.Navigation.OnAbout.GoBack.authClass
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import isel.pdm.pokerdice.ui.viewmodels.MatchViewModel
import java.util.UUID

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
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Request the permission
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }
    }

    @Composable
    fun SessionVerification(
        authenticatedScreen: @Composable (User) -> Unit,
        nonAuthenticatedScreen: @Composable () -> Unit = {
            SessionExpiredScreen(
                onClick={toClearScreen(authClass,Anim.Backwards)}
            )
        }
    ) {
        SessionLog.logInfo("Session Verification")
        authViewModel.getCurrentUser()
        PokerDiceTheme {
            val state by authViewModel.state.collectAsState()
            when (state) {
                is AuthViewModel.State.LoggedIn -> {
                    SessionLog.logInfo("User Authenticated")
                    authenticatedScreen((state as AuthViewModel.State.LoggedIn).user)
                }
                is AuthViewModel.State.LoggingIn -> {
                    SessionLog.logInfo("User in Authentication Process")
                    DefaultCircularProgressIndicator()
                }
                else -> {
                    SessionLog.logInfo("User NOT Authenticated")
                    nonAuthenticatedScreen()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun MyLobby(user:User){
        intent
            .getParcelableExtra("LOBBY_UUID",UUID::class.java)
            ?.let { lobbyViewModel.joinLobby(it, user); LobbyLog.logInfo("Joining Lobby") }
        lobbyViewModel.getCurrentLobby(user)
        val state by lobbyViewModel.state.collectAsState()
        when(state){
            else->TODO("1. With lobby; 2.Without Lobby; !Correct State manegment!")
        }

    }

    @Composable
    fun MyMatch(){
        TODO()
    }
}