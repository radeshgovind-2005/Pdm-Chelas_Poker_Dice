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
import isel.pdm.chelaspokerdice.ui.components.ShowError
import isel.pdm.chelaspokerdice.ui.components.figures.PokerDiceLogo
import isel.pdm.chelaspokerdice.ui.components.struct.contentDisplay.ScrollableContentColumnDisplay
import isel.pdm.chelaspokerdice.ui.components.struct.textfield.SimpleRoundedTextField
import isel.pdm.chelaspokerdice.ui.components.struct.textfield.TextFieldColumn
import isel.pdm.chelaspokerdice.vm.LobbyViewModel

const val CREATE_LOBBY_SCAFFOLD_TAG = "create_lobby_scaffold"
const val CREATE_LOBBY_CONTENT_TAG = "create_lobby_content"

class CreateLobbyScreen(
    private val onNavigateToLobbies: () -> Unit = {},
    private val onNavigateToLobby: (lobbyId: String) -> Unit = {},
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

        var nameError by remember { mutableStateOf<String?>(null) }
        var descriptionError by remember { mutableStateOf<String?>(null) }
        var playersError by remember { mutableStateOf<String?>(null) }
        var roundsError by remember { mutableStateOf<String?>(null) }

        // Watch for errors from ViewModel
        val errorState = lobbyViewModel.state as? LobbyViewModel.State.Error

        LaunchedEffect(errorState) {
            errorState?.exception?.message?.let { errorMessage ->
                // You could parse the error message to determine which field failed
                // For now, we'll show a general error
            }
        }
        LaunchedEffect(lobbyViewModel.state) {
            if (lobbyViewModel.state is LobbyViewModel.State.InLobby) {
                val lobbyState = lobbyViewModel.state as LobbyViewModel.State.InLobby
                onNavigateToLobby(lobbyState.lobby.id.toString())
            }
        }
        fun validateName(value: String): Boolean {
            return if (value.isBlank()) {
                nameError = "Name cannot be blank"
                false
            } else {
                nameError = null
                true
            }
        }

        fun validateDescription(value: String): Boolean {
            return if (value.isBlank()) {
                descriptionError = "Description cannot be blank"
                false
            } else {
                descriptionError = null
                true
            }
        }

        fun validateExpectedPlayers(value: String): Boolean {
            val intValue = value.toIntOrNull()
            return when {
                value.isBlank() -> {
                    playersError = "Expected players cannot be blank"
                    false
                }

                intValue == null -> {
                    playersError = "Expected players must be a valid number"
                    false
                }

                intValue < 2 -> {
                    playersError = "Expected players must be at least 2"
                    false
                }

                intValue > 6 -> {
                    playersError = "Expected players cannot exceed 6"
                    false
                }

                else -> {
                    playersError = null
                    true
                }
            }
        }

        fun validateNumberOfRounds(value: String): Boolean {
            val intValue = value.toIntOrNull()
            val expectedPlayersInt = expectedPlayers.toIntOrNull() ?: 0
            return when {
                value.isBlank() -> {
                    roundsError = "Number of rounds cannot be blank"
                    false
                }

                intValue == null -> {
                    roundsError = "Number of rounds must be a valid number"
                    false
                }

                intValue <= 0 -> {
                    roundsError = "Number of rounds must be positive"
                    false
                }

                intValue > 60 -> {
                    roundsError = "Number of rounds cannot exceed 60"
                    false
                }

                expectedPlayersInt > 0 && intValue % expectedPlayersInt != 0 -> {
                    roundsError =
                        "Number of rounds must be a multiple of expected players ($expectedPlayersInt)"
                    false
                }

                else -> {
                    roundsError = null
                    true
                }
            }
        }

        // Real-time validation as user types
        fun onNameChange(value: String) {
            lobbyName = value
            validateName(value)
        }

        fun onDescriptionChange(value: String) {
            lobbyDescription = value
            validateDescription(value)
        }

        fun onExpectedPlayersChange(value: String) {
            expectedPlayers = value
            validateExpectedPlayers(value)
            // Re-validate rounds when players change (due to multiple constraint)
            if (numberOfRounds.isNotBlank()) {
                validateNumberOfRounds(numberOfRounds)
            }
        }

        fun onNumberOfRoundsChange(value: String) {
            numberOfRounds = value
            validateNumberOfRounds(value)
        }

        fun isFormValid(): Boolean {
            return validateName(lobbyName) &&
                    validateDescription(lobbyDescription) &&
                    validateExpectedPlayers(expectedPlayers) &&
                    validateNumberOfRounds(numberOfRounds)
        }

        fun onCreateLobby() {
            if (isFormValid()) {
                lobbyViewModel.createLobby(
                    name = lobbyName,
                    description = lobbyDescription,
                    expectedPlayers = expectedPlayers,
                    numberOfRounds = numberOfRounds
                )
            }
        }

        val isFormValid = nameError == null &&
                descriptionError == null &&
                playersError == null &&
                roundsError == null &&
                lobbyName.isNotBlank() &&
                lobbyDescription.isNotBlank() &&
                expectedPlayers.isNotBlank() &&
                numberOfRounds.isNotBlank()

        CreateLobbyScaffold(
            Modifier.testTag(CREATE_LOBBY_SCAFFOLD_TAG),
            onNavigateToLobbies,
            { onCreateLobby() },
            isFormValid
        ) { innerPadding ->
            ScrollableContentColumnDisplay(
                Modifier.testTag(CREATE_LOBBY_CONTENT_TAG),
                innerPadding,
                Arrangement.Top
            ) {

                Spacer(Modifier.height(64.dp))
                PokerDiceLogo()
                Spacer(Modifier.height(32.dp))
                TextFieldColumn {
                    TextFieldColumn {
                        // Name field with error
                        Column {
                            SimpleRoundedTextField(
                                value = lobbyName,
                                onValueChange = ::onNameChange,
                                label = "Lobby Name",
                                isError = nameError != null
                            )
                            nameError?.ShowError()
                        }

                        Spacer(Modifier.height(16.dp))

                        // Description field with error
                        Column {
                            SimpleRoundedTextField(
                                value = lobbyDescription,
                                onValueChange = ::onDescriptionChange,
                                label = "Lobby Description",
                                isError = descriptionError != null
                            )
                            descriptionError?.ShowError()
                        }

                        Spacer(Modifier.height(16.dp))

                        // Expected players field with error
                        Column {
                            SimpleRoundedTextField(
                                value = expectedPlayers,
                                onValueChange = ::onExpectedPlayersChange,
                                label = "Expected Players",
                                keyboardType = KeyboardType.Number,
                                isError = playersError != null
                            )
                            playersError?.ShowError()
                        }

                        Spacer(Modifier.height(16.dp))

                        // Number of rounds field with error
                        Column {
                            SimpleRoundedTextField(
                                value = numberOfRounds,
                                onValueChange = ::onNumberOfRoundsChange,
                                label = "Number of Rounds",
                                keyboardType = KeyboardType.Number,
                                isError = roundsError != null
                            )
                            roundsError?.ShowError()
                        }
                    }
                }
            }
            content()
        }
    }
}
