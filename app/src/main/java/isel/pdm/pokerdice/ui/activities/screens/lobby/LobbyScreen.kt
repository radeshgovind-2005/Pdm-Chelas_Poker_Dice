package isel.pdm.pokerdice.ui.activities.screens.lobby

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import isel.pdm.pokerdice.ui.components.text.BoldTitle
import isel.pdm.pokerdice.ui.layouts.screens.DefaultBackScreen
import isel.pdm.pokerdice.ui.viewmodels.lobby.LobbyState

@Composable
fun LobbyScreen(
    state: LobbyState,
    onBackClick: () -> Unit,
    onJoinRequest: () -> Unit,
    onStartMatch: () -> Unit
) {
    DefaultBackScreen(
        title = stringResource(R.string.lobby_title),
        onClick = onBackClick,
        content = {
            val scrollState = rememberScrollState()
            SimpleCard(Modifier.fillMaxSize(0.9f).verticalScroll(scrollState)) {
                when{
                    state.isLoading -> {
                        Column(
                            modifier=Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            CircularProgressIndicator()
                        }
                    }
                    state.error != null ->{
                        Column(
                            modifier=Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(state.error)
                        }
                    }
                    state.lobby != null ->{
                        Column(
                            modifier=Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            BoldTitle(state.lobby.name)
                            Text(state.lobby.description)

                            Row(Modifier.fillMaxWidth().padding(32.dp)) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(0.5f),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text("${stringResource(R.string.lobby_rounds)}: ${state.lobby.maxRounds}")
                                    Text("${stringResource(R.string.lobby_balance)}: ${state.lobby.initialBalance}")
                                    Text("${stringResource(R.string.lobby_ante)}: ${state.lobby.ante}")
                                }
                                Column {
                                    Text("${stringResource(R.string.lobby_players)}(${state.lobby.players.size}/${state.lobby.maxPlayers})")
                                    state.lobby.players.forEach {
                                        Row(Modifier.fillMaxWidth()) {
                                            Icon(Icons.Default.Person, "Player")
                                            Text(it)
                                        }

                                    }
                                }
                            }





                            Row(
                                modifier = Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    val txt = stringResource(if(state.isJoined) R.string.lobby_start else R.string.lobby_join)
                                    val enabled = when{
                                        state.isJoined
                                                && state.lobby.maxPlayers == state.lobby.players.size
                                                && state.lobby.host == state.username-> true
                                        !state.isJoined && state.lobby.maxPlayers > state.lobby.players.size-> true
                                        else -> false
                                    }
                                    Button(
                                        onClick = {
                                            if(!state.isJoined)
                                                onJoinRequest()
                                            else{
                                                onStartMatch()
                                            }
                                        },
                                        enabled = enabled,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(txt)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}