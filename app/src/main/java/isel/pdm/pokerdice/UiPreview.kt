package isel.pdm.pokerdice

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import isel.pdm.pokerdice.ui.activities.screens.about.AboutScreen
import isel.pdm.pokerdice.ui.activities.screens.lobbies.LobbiesScreen
import isel.pdm.pokerdice.ui.activities.screens.lobby.LobbyScreen
import isel.pdm.pokerdice.ui.activities.screens.title.TitleScreen
import isel.pdm.pokerdice.ui.components.error.DefaultErrorContent
import isel.pdm.pokerdice.ui.theme.PokerDiceTheme
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel

enum class ScreensPreview { TitleScreen, AboutScreen, LobbiesScreen, LobbyScreen }

@Preview
@Composable
fun Previewer(){

    val screen: ScreensPreview = ScreensPreview.LobbyScreen
    val lvm = LobbyViewModel()
    PokerDiceTheme {
        when (screen) {
            ScreensPreview.TitleScreen -> TitleScreen()
            ScreensPreview.AboutScreen -> AboutScreen()
            ScreensPreview.LobbiesScreen -> LobbiesScreen(viewModel =lvm)
            ScreensPreview.LobbyScreen -> LobbyScreen(viewModel =lvm)
            //ScreensPreview.Error -> DefaultErrorContent("error"){}
        }
    }
}