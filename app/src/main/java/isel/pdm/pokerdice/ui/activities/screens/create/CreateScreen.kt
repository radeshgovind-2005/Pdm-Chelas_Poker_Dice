package isel.pdm.pokerdice.ui.activities.screens.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.SimpleCard
import isel.pdm.pokerdice.ui.components.text.textfield.LobbyAnteTextField
import isel.pdm.pokerdice.ui.components.text.textfield.LobbyBalanceTextField
import isel.pdm.pokerdice.ui.components.text.textfield.LobbyDescriptionTextField
import isel.pdm.pokerdice.ui.components.text.textfield.LobbyExpectedPlayersTextField
import isel.pdm.pokerdice.ui.components.text.textfield.LobbyNameTextField
import isel.pdm.pokerdice.ui.components.text.textfield.LobbyRoundsTextField
import isel.pdm.pokerdice.ui.layouts.screens.DefaultBackScreen
import isel.pdm.pokerdice.ui.viewmodels.create.CreateState

@Composable
fun CreateScreen(
    state: CreateState,
    onBackRequest: () -> Unit,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onExpectedPlayersChange: (Int) -> Unit,
    onMaxRoundsChange: (Int) -> Unit,
    onBalanceChange: (Int) -> Unit,
    onAnteChange: (Int) -> Unit,
    onCreateRequest: () -> Unit,
    onTryAgain: () -> Unit
) {
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.create_title) }
    val alertTitleResId by rememberSaveable { mutableIntStateOf(R.string.alert_error_title) }
    val alertBodyResId by rememberSaveable { mutableIntStateOf(R.string.alert_error_unknown) }
    val alertBtnResId by rememberSaveable { mutableIntStateOf(R.string.alert_error_btn) }
    val titleStr=stringResource(titleResId)
    if (state.error != null) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text(stringResource(alertTitleResId)) },
            text = { Text(state.error ?: stringResource(alertBodyResId)) },
            confirmButton = {
                TextButton(onClick = onTryAgain) {
                    Text(stringResource(alertBtnResId))
                }
            },
        )
    }

    DefaultBackScreen(
        title= titleStr,
        onClick = onBackRequest,
        content = {
            val scrollState = rememberScrollState()
            SimpleCard(Modifier.fillMaxSize(0.9f).verticalScroll(scrollState)){
                Column(
                    modifier=Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    LobbyNameTextField(state, onNameChange)
                    LobbyDescriptionTextField(state, onDescriptionChange)
                    LobbyExpectedPlayersTextField(state, onExpectedPlayersChange)
                    LobbyRoundsTextField(state, onMaxRoundsChange)
                    LobbyBalanceTextField(state, onBalanceChange)
                    LobbyAnteTextField(state, onAnteChange)
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator()
                        } else {
                            Button(
                                onClick = onCreateRequest,
                                enabled = state.isCreateEnabled,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(titleStr)
                            }
                        }
                    }
                }

            }
        }
    )
}