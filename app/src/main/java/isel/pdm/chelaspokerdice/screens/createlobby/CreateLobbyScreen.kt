package isel.pdm.chelaspokerdice.screens.createlobby

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.screens.Screen
import isel.pdm.chelaspokerdice.screens.createlobby.struct.CreateLobbyScaffold
import isel.pdm.chelaspokerdice.screens.lobbies.LobbiesScreen
import isel.pdm.chelaspokerdice.ui.components.figures.PokerDiceLogo
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.textfield.SimpleRoundedTextField
import isel.pdm.chelaspokerdice.ui.components.struct.textfield.TextFieldColumn
import isel.pdm.chelaspokerdice.vm.LobbyViewModel

const val CREATE_LOBBY_SCAFFOLD_TAG = "create_lobby_scaffold"
const val CREATE_LOBBY_CONTENT_TAG = "create_lobby_content"

class CreateLobbyScreen(
    private val onNavigateToLobbies: () -> Unit = {},
    private val onNavigateToLobby: () -> Unit = {},
    private val lobbyViewModel: LobbyViewModel
) : Screen {

    @Composable
    override fun PortraitScreen(modifier: Modifier) {
        CreateLobbyScreen {

        }
    }

    @Composable
    override fun LandscapeScreen(modifier: Modifier) {
        CreateLobbyScreen {
        }
    }

    @SuppressLint("NotConstructor")
    @Composable
    private fun CreateLobbyScreen(content: @Composable () -> Unit) {

        var lobbyName by remember { mutableStateOf("") }
        var lobbyDescription by remember { mutableStateOf("") }
        var expectedPlayers by remember { mutableStateOf("") }
        var numberOfRounds by remember { mutableStateOf("") }

        CreateLobbyScaffold(Modifier.testTag(CREATE_LOBBY_SCAFFOLD_TAG), onNavigateToLobbies, {
            lobbyViewModel.createLobby(
                name = lobbyName,
                description = lobbyDescription,
                expectedPlayers = expectedPlayers,
                numberOfRounds = numberOfRounds
            )
            onNavigateToLobby()
        }) { innerPadding ->
            ScrollableContentColumnDisplay(Modifier.testTag(CREATE_LOBBY_CONTENT_TAG), innerPadding, Arrangement.Top) {

                Spacer(Modifier.height(64.dp))
                PokerDiceLogo()
                Spacer(Modifier.height(32.dp))
                TextFieldColumn {
                    SimpleRoundedTextField(lobbyName,{lobbyName=it}, "Lobby Name")
                    SimpleRoundedTextField(lobbyDescription,{lobbyDescription=it}, "Lobby Description")
                    SimpleRoundedTextField(expectedPlayers, {expectedPlayers=it}, "Expected Players", KeyboardType.Number)
                    SimpleRoundedTextField(numberOfRounds, {numberOfRounds=it}, "Number of Rounds", KeyboardType.Number)
                }
                content()
            }
        }
    }
}

