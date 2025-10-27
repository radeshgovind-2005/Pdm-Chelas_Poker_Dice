package isel.pdm.chelaspokerdice.screens.lobbies

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.lobbies.struct.LobbiesScaffold
import isel.pdm.chelaspokerdice.screens.lobbies.struct.ShowLobbies
import isel.pdm.chelaspokerdice.ui.components.elements.LargeText
import isel.pdm.chelaspokerdice.ui.components.elements.MediumText
import isel.pdm.chelaspokerdice.ui.components.struct.SimpleSearchBar
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ContentColumnDisplay
import isel.pdm.chelaspokerdice.vm.LobbyViewModel


const val ADD_BUTTON_VIEW = "add button"
const val LOBBIES_SCAFFOLD_TAG = "lobbies_scaffold"
const val LOBBIES_CONTENT_TAG = "lobbies_content"

class LobbiesScreen(
    private val onNavigateToTitleScreen: () -> Unit = {},
    private val onNavigateToCreateLobby: () -> Unit = {},
    private val onNavigateToLobby: (lobbyId: String) -> Unit = {},
    private val lobbyViewModel: LobbyViewModel
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        LobbiesScreenContent {

        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        LobbiesScreenContent {
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun LobbiesScreenContent(content: @Composable () -> Unit) {
        val state = lobbyViewModel.state
        LobbiesScaffold(
            modifier = Modifier.testTag(LOBBIES_SCAFFOLD_TAG),
            onNavigateToTitleScreen,
            onNavigateToCreateLobby
        ) { innerPadding ->
            ContentColumnDisplay(
                Modifier.testTag(LOBBIES_CONTENT_TAG),
                innerPadding,
                Arrangement.Top
            ) {
                SimpleSearchBar(lobbyViewModel)
                Spacer(Modifier.height(16.dp))
                when (state) {
                    is LobbyViewModel.State.LobbiesLoaded -> ShowLobbies(state,{lobby -> onNavigateToLobby(lobby.id.toString())})
                    is LobbyViewModel.State.LoadingLobbies -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            RotatingProgressIndicator()

                            Spacer(Modifier.height(64.dp))
                            LargeText("Loading Lobbies...")
                        }
                    }
                    else -> { }
                }
            }
            content()
        }
    }
}

@Composable
fun RotatingProgressIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing)
        ),
        label = "rotation"
    )

    CircularProgressIndicator(
        modifier = Modifier
            .size(64.dp)
            .rotate(rotation),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        strokeWidth = 6.dp
    )
}
