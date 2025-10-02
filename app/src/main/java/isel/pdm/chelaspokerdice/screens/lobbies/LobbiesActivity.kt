package isel.pdm.chelaspokerdice.screens.lobbies

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.ui.navigation.ActivityNavigator
import isel.pdm.chelaspokerdice.ui.navigation.LobbiesNavigation
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class LobbiesActivity : ActivityNavigator() {

    private val tag = this::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(tag, "LobbiesActivity.onCreate.")
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                LobbiesScreen(
                    onNavigateToTitleScreen = { navigate(LobbiesNavigation.ToTitleScreen) }
                ).Render(Modifier)
            }
        }
    }

    private fun navigate(nav: LobbiesNavigation) {
        when (nav) {
            LobbiesNavigation.ToTitleScreen -> finish()
        }
    }
}