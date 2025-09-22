package isel.pdm.chelaspokerdice.activities.playerprofile.compose

import androidx.compose.runtime.Composable
import isel.pdm.chelaspokerdice.components.scaffold.DefaultScaffold

@Composable
fun LandscapePlayerProfileCompose(
    code: @Composable () -> Unit
) {
    DefaultScaffold("Player Profile", code = { code() })
}