package isel.pdm.pokerdice.ui.activities.screens.lobby

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.error.DefaultErrorContent
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.layout.AdaptiveLayoutContent
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.components.progressindicator.DefaultCircularProgressIndicator
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel


@Composable
fun LobbyScreen(
    navBack: () -> Unit = {},
    navToGame: () -> Unit = {},
    lvm: LobbyViewModel,
    avm: AuthViewModel
) {
    CommonLayout({
        val user = (avm.state as? AuthViewModel.State.LoggedIn)?.user
        Log.d("LOBBY_SCREEN", "user: $user")
        if (user != null)
            lvm.leaveLobby(user)
        navBack()
    }){ padding ->
        when(val state = lvm.state){
            LobbyViewModel.State.LeavingLobby -> { DefaultCircularProgressIndicator()}
            LobbyViewModel.State.Idle -> { DefaultCircularProgressIndicator()}
            LobbyViewModel.State.LoadingLobby -> { DefaultCircularProgressIndicator()}
            is LobbyViewModel.State.LobbyLoaded -> {
                AdaptiveLayoutContent(
                    landscape = {LandscapeLobbyContent(state.lobby, navToGame,padding = padding)},
                    portrait = {PortraitLobbyContent(state.lobby, navToGame,padding = padding)}
                )

            }
            is LobbyViewModel.State.Error -> {DefaultErrorContent(state.e.toString(),padding = padding)}
            else -> {DefaultErrorContent(RememberString(R.string.invalid_state),padding = padding) }
        }
    }
}

@Composable
private fun CommonLayout(navBack: () -> Unit, content: @Composable (PaddingValues) -> Unit){
    DefaultLayout(
        topbar = {
            DefaultTopBar(
                title= RememberString(R.string.lobby_screen_title),
                navIcon = MyIcon.Back,
                onClick = navBack
            )
        },
    ){ innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            content(innerPadding)
        }
    }
}
