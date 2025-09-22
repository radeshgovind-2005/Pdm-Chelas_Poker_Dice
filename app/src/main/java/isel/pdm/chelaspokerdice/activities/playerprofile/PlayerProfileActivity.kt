package isel.pdm.chelaspokerdice.activities.playerprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.components.navigation.NavigationManager
import isel.pdm.chelaspokerdice.components.navigation.Route
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class PlayerProfileActivity : ComponentActivity() {
    private val navMap: Map<() -> Unit, Int>
        @Composable
        get() = mapOf(
            { NavigationManager.navigate(this, Route.TitleScreen) } to R.string.title_screen,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                PlayerProfileScreen(navMap).Render(Modifier)
            }
        }
    }

}