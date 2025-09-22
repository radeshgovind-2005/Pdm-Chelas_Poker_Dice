package isel.pdm.chelaspokerdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import isel.pdm.chelaspokerdice.activities.title.TitleScreen
import isel.pdm.chelaspokerdice.components.navigation.NavigationManager
import isel.pdm.chelaspokerdice.components.navigation.Route
import isel.pdm.chelaspokerdice.ui.theme.ChelasPokerDiceTheme

class MainActivity : ComponentActivity() {

    private val tag = this::class.java.simpleName

    private val navMap: Map<()->Unit,Int>
        @Composable
        get() = mapOf(
            { NavigationManager.navigate(this, Route.PlayerProfile) } to R.string.profile,
            { NavigationManager.navigate(this, Route.Lobbies) } to R.string.lobbies,
            { NavigationManager.navigate(this, Route.About) } to R.string.about
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChelasPokerDiceTheme {
                TitleScreen(navMap, this).Render(Modifier)
            }
        }
    }
}
