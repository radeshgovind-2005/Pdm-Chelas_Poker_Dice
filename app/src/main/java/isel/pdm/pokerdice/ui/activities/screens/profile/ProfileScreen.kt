package isel.pdm.pokerdice.ui.activities.screens.profile

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
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.SimpleCard
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
    val titleResId by rememberSaveable { mutableIntStateOf(R.string.profile_title) }

    if (state.showLogoutDialog) {
        AlertDialog(
            onDismissRequest = onLogoutCancel,
            title = { Text("Logout") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                TextButton(onClick = onLogoutConfirm) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = onLogoutCancel) {
                    Text("No")
                }
            }
        )
    }

    DefaultBackScreen(
        title = stringResource(titleResId),
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
            SimpleCard {

            }
        }
    )
}