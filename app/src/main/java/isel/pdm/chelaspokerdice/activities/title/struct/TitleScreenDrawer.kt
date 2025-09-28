package isel.pdm.chelaspokerdice.activities.title.struct

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import isel.pdm.chelaspokerdice.R
import isel.pdm.chelaspokerdice.components.struct.drawer.DrawerMenuItem
import isel.pdm.chelaspokerdice.components.struct.drawer.SimpleDrawer


@Composable
fun TitleScreenDrawer(
    onNavigateToPlayerProfile: () -> Unit,
    onNavigateToAbout: () -> Unit,
    code: @Composable (onOpenDrawer: () -> Unit) -> Unit,
) {
    val menuItems = arrayOf(
        DrawerMenuItem(
            "Profile",
            stringResource(R.string.profile),
            onNavigateToPlayerProfile
        ),
        DrawerMenuItem(
            id = "about",
            label = stringResource(R.string.about),
            action = onNavigateToAbout
        )
    )

    SimpleDrawer(
        title = "Menu",
        menuItems = menuItems,
        content = code
    )
}