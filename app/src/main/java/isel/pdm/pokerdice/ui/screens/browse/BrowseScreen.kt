package isel.pdm.pokerdice.ui.screens.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.common.components.card.SimpleCard
import isel.pdm.pokerdice.ui.common.layouts.screens.DefaultBackScreen
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseLobbiesScreen(
    state: BrowseState,
    onBackClick: () -> Unit,
    onSearch: () -> Unit,
    onQueryChange: (String) -> Unit,
    onCreateLobby: () -> Unit,
    onLobbyClick: (String) -> Unit
) {
    DefaultBackScreen(
        title = stringResource(R.string.browse_title),
        onClick=onBackClick,
        fab={
            FloatingActionButton(
                onClick = onCreateLobby,
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create"
                )
            }
        },
        content = {
            SimpleCard {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ){
                    SearchLobby(state, onQueryChange, onSearch)
                    Column(
                        modifier=Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when {
                            state.isLoading -> CircularProgressIndicator()
                            !state.isLoading && state.filteredLobbies.isEmpty() -> Text(stringResource(R.string.browse_no_lobbies))
                            !state.isLoading -> ShowLobbies(state.filteredLobbies, onLobbyClick)
                        }
                    }

                }

            }
        }
    )
}

