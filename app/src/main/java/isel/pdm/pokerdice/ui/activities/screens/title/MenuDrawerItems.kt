package isel.pdm.pokerdice.ui.activities.screens.title

import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.drawer.DrawerMenuItem
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun MenuDrawerItems(
    NavToPlayerProfile: () -> Unit,
    NavToAbout: () -> Unit
): Array<DrawerMenuItem> = arrayOf(
    DrawerMenuItem(
        label = RememberString(R.string.drawer_player_profile),
        action = NavToPlayerProfile
    ),
    DrawerMenuItem(
        label = RememberString(R.string.drawer_about),
        action = NavToAbout
    )
)