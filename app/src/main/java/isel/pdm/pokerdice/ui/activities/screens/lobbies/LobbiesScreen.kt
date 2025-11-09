package isel.pdm.pokerdice.ui.activities.screens.lobbies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.error.DefaultErrorContent
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.components.progressindicator.DefaultCircularProgressIndicator
import isel.pdm.pokerdice.ui.components.searchbar.DefaultSearchbar
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import java.util.UUID

@Composable
fun LobbiesScreen(
    navBack: () -> Unit = {},
    navToLobby: (id: UUID) -> Unit = {},
    navToCreateLobby: () -> Unit = {},
    viewModel: LobbyViewModel
) {

    fun load() { viewModel.loadLobbies() }
    CommonLayout(navBack,navToCreateLobby){ padding ->
        DefaultSearchbar(padding,{viewModel.searchLobbies(it)})
        when(val state = viewModel.state){
            LobbyViewModel.State.Idle -> {load()}
            LobbyViewModel.State.LoadingLobbies -> { DefaultCircularProgressIndicator()}
            LobbyViewModel.State.SearchingLobbies -> { DefaultCircularProgressIndicator()}
            is LobbyViewModel.State.LobbiesLoaded -> {
                LobbiesContent(state.lobbies, { navToLobby(it) })
            }
            is LobbyViewModel.State.Error -> {DefaultErrorContent(state.e.toString(),padding = padding){load()} }
            else -> {DefaultErrorContent(RememberString(R.string.invalid_state), padding = padding) }
        }
    }
}

@Composable
private fun CommonLayout(
    navBack: () -> Unit,
    navBtn: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    DefaultLayout(
        topbar = {
            DefaultTopBar(
                title= RememberString(R.string.lobbies_screen_title),
                navIcon = MyIcon.Back,
                onClick = navBack
            )
        },
        floatingBtn = {
            FloatingActionButton(
                onClick = navBtn,
                containerColor = Color.Green
            ) { SimpleIcon(MyIcon.Create)}

        }
    ){ innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            content(innerPadding)
        }
    }
}
