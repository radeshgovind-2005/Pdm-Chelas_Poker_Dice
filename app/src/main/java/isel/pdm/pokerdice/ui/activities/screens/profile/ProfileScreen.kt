package isel.pdm.pokerdice.ui.activities.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import isel.pdm.pokerdice.ui.components.text.BoldTitle
import isel.pdm.pokerdice.ui.layouts.screens.DefaultBackScreen
import isel.pdm.pokerdice.ui.theme.RedishBrown
import isel.pdm.pokerdice.ui.viewmodels.profile.ProfileState

@Composable
fun ProfileScreen(
    state: ProfileState,
    onBackClick: () -> Unit,
    onLogoutRequest: () -> Unit,
    onLogoutConfirm: () -> Unit,
    onLogoutCancel: () -> Unit
    ) {
    if (state.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = onLogoutCancel,
            title = { Text(stringResource(R.string.profile_logout)) },
            text = { Text(stringResource(R.string.profile_logout_text)) },
            confirmButton = {
                TextButton(onClick = onLogoutConfirm) {
                    Text(stringResource(R.string.affirmative))
                }
            },
            dismissButton = {
                TextButton(onClick = onLogoutCancel) {
                    Text(stringResource(R.string.negative))
                }
            }
        )
    }

    DefaultBackScreen(
        title = stringResource(R.string.profile_title),
        onClick = onBackClick,
        fab = {
            FloatingActionButton(
                onClick = onLogoutRequest,
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.surface
                ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = "Logout"
                )
            }
        },
        content = {
            val scrollState = rememberScrollState()
            SimpleCard {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(Modifier.padding(16.dp)){
                        BoldTitle("${stringResource(R.string.profile_stats)} ${state.username}")
                    }
                    Text("${stringResource(R.string.profile_g_played)}: ${state.stats?.gamesPlayed}")
                    Text("${stringResource(R.string.profile_m_won)}: ${state.stats?.matchesWon}")
                    Text("${stringResource(R.string.profile_w_rate)}: ${state.stats?.winRate}")
                    Text("${stringResource(R.string.profile_r_won)}: ${state.stats?.roundsWon}")
                    Text("${stringResource(R.string.profile_l_hosted)}: ${state.stats?.lobbiesHosted}")
                    Text("${stringResource(R.string.profile_n_invites)}: ${state.stats?.invitesSent}")
                    Text("${stringResource(R.string.profile_e_hands)}: ${state.stats?.epicHands}")
                }
            }
        }
    )
}