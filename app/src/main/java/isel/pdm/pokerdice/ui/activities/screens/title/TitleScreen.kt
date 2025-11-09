package isel.pdm.pokerdice.ui.activities.screens.title

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.drawer.DefaultDrawer
import isel.pdm.pokerdice.ui.components.drawer.DrawerMenuItem
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.layout.AdaptiveLayoutContent
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar

@Composable
fun TitleScreen(
    navToPlayerProfile: () -> Unit = {},
    navToAbout: () -> Unit = {},
    navToLobbies: () -> Unit = {},
) {
    CommonLayout(
        items = MenuDrawerItems(navToPlayerProfile, navToAbout)
    ) { innerPadding ->
        AdaptiveLayoutContent(
            landscape = { TitleLandscapeContent(innerPadding, navToLobbies) },
            portrait = { TitlePortraitContent(navToLobbies) }
        )
    }

}

@Composable
private fun CommonLayout(items: Array<DrawerMenuItem>, content: @Composable (PaddingValues) -> Unit){
    DefaultDrawer(
        title = RememberString(R.string.drawer_menu_title),
        menuItems =items
    ){ onOpen ->
        DefaultLayout(
            topbar = { DefaultTopBar(navIcon = MyIcon.Drawer, onClick = onOpen) }
        ){ innerPadding ->
            content(innerPadding)
        }
    }
}
