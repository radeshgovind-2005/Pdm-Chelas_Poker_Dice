package isel.pdm.pokerdice.ui.activities.screens.createlobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.domain.values.isValidDescription
import isel.pdm.pokerdice.domain.values.isValidExpectedPlayers
import isel.pdm.pokerdice.domain.values.isValidName
import isel.pdm.pokerdice.domain.values.isValidNumberOfRounds
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.images.PokerDiceLogo
import isel.pdm.pokerdice.ui.components.inputs.types.DescriptionTextField
import isel.pdm.pokerdice.ui.components.inputs.types.ExpectedPlayerTextField
import isel.pdm.pokerdice.ui.components.inputs.types.NameTextField
import isel.pdm.pokerdice.ui.components.inputs.types.NumberOfRoundsTextField
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.viewmodels.AuthViewModel
import isel.pdm.pokerdice.ui.viewmodels.LobbyViewModel
import java.util.UUID

@Composable
fun CreateLobbyScreen(
    navBack: () -> Unit = {},
    navToLobby: (id: UUID) -> Unit = {},
    lvm: LobbyViewModel,
    avm: AuthViewModel
) {
    val avmState by avm.state.collectAsState()
    val lvmState by lvm.state.collectAsState()
    var lobbyName by rememberSaveable  { mutableStateOf("") }
    var lobbyDescription by rememberSaveable { mutableStateOf("") }
    var expectedPlayers by rememberSaveable { mutableStateOf("") }
    var numberOfRounds by rememberSaveable { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var descriptionError by remember { mutableStateOf<String?>(null) }
    var playersError by remember { mutableStateOf<String?>(null) }
    var roundsError by remember { mutableStateOf<String?>(null) }

    var lobbyNameTouched by remember { mutableStateOf(false) }
    var lobbyDescriptionTouched by remember { mutableStateOf(false) }
    var expectedPlayersTouched by remember { mutableStateOf(false) }
    var numberOfRoundsTouched by remember { mutableStateOf(false) }


    LaunchedEffect(
        lobbyName, lobbyDescription, expectedPlayers, numberOfRounds,
        lobbyNameTouched,lobbyDescriptionTouched,expectedPlayersTouched,numberOfRoundsTouched
    ) {
        if(lobbyNameTouched)
            nameError = lobbyName.isValidName()?.message
        if(lobbyDescriptionTouched)
            descriptionError = lobbyDescription.isValidDescription()?.message
        if(expectedPlayersTouched)
            playersError = expectedPlayers.toIntOrNull()?.isValidExpectedPlayers()?.message
        if(numberOfRoundsTouched) {
            val expectedPlayersInt = expectedPlayers.toIntOrNull() ?: return@LaunchedEffect
            roundsError =
                numberOfRounds.toIntOrNull()?.isValidNumberOfRounds(expectedPlayersInt)?.message
        }
    }

    val isFormValid = nameError == null && descriptionError == null &&
            playersError == null && roundsError == null &&
            lobbyName.isNotBlank() && lobbyDescription.isNotBlank() &&
            expectedPlayers.isNotBlank() && numberOfRounds.isNotBlank() &&
            lobbyNameTouched && lobbyDescriptionTouched &&
            expectedPlayersTouched && numberOfRoundsTouched

    CommonLayout(navBack,{
        if (isFormValid && avmState is AuthViewModel.State.LoggedIn) {
            lvm.insertLobby(
                    name = lobbyName,
                    description = lobbyDescription,
                    expectedPlayers = expectedPlayers,
                    numberOfRounds = numberOfRounds,
                    host = (avmState as AuthViewModel.State.LoggedIn).user
                )
            if(lvmState is LobbyViewModel.State.CreatedLobby)
                navToLobby((lvmState as LobbyViewModel.State.CreatedLobby).lobby.id)
        }
    },isFormValid){ padding ->
        OneFullColumn(padding) {

            PokerDiceLogo()
            Spacer(Modifier.height(32.dp))

            NameTextField(
                value = lobbyName,
                onValueChange = {lobbyName = it; lobbyNameTouched = true},
                errorMessage = nameError,
                label = RememberString(R.string.create_lobby_label_name)
            )
            DescriptionTextField(
                value = lobbyDescription,
                onValueChange = {lobbyDescription = it; lobbyDescriptionTouched = true},
                errorMessage = descriptionError,
                label = RememberString(R.string.create_lobby_label_description)
            )
            ExpectedPlayerTextField(
                value = expectedPlayers,
                onValueChange = { expectedPlayers=it; expectedPlayersTouched = true },
                errorMessage = playersError,
            )
            NumberOfRoundsTextField(
                expectedPlayers= expectedPlayers,
                value = numberOfRounds,
                onValueChange = { numberOfRounds=it; numberOfRoundsTouched = true },
                errorMessage = roundsError,
            )
        }
    }
}

@Composable
private fun CommonLayout(
    navBack: () -> Unit,
    navLobby: () -> Unit,
    enabled: Boolean = true,
    content: @Composable (PaddingValues) -> Unit
){
    DefaultLayout(
        topbar = {
            DefaultTopBar(
                title= RememberString(R.string.create_lobby_title),
                navIcon = MyIcon.Back,
                onClick = navBack
            )
        },
        floatingBtn = {
            Row (
                modifier = Modifier.fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.Center
            ){
                ButtonText(
                    text=stringResource(R.string.create_lobby_btn),
                    modifier = Modifier.size(300.dp,50.dp),
                    enabled = enabled,
                    onClick = navLobby
                )
            }

        }
    ){ innerPadding ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            content(innerPadding)
        }
    }
}
