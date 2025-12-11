package isel.pdm.pokerdice.ui.activities.screens.title

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.card.SimpleCard
import isel.pdm.pokerdice.ui.components.drawer.Info
import isel.pdm.pokerdice.ui.components.drawer.Person
import isel.pdm.pokerdice.ui.components.drawer.SimpleDrawer
import isel.pdm.pokerdice.ui.layouts.AdaptiveLayout
import isel.pdm.pokerdice.ui.layouts.screens.SimpleScreen
import isel.pdm.pokerdice.ui.viewmodels.title.TitleState
import kotlinx.coroutines.launch

@Composable
fun TitleScreen(
    state: TitleState,
    onClickAbout: () -> Unit,
    onClickLobbies: () -> Unit,
    onClickProfile: () -> Unit
) {
    val btnResId =R.string.title_btn
    val items by lazy {
        listOf(
            Person(R.string.title_item1,onClickProfile),
            Info(R.string.title_item2,onClickAbout)
        )
    }
    SimpleDrawer(R.string.title_drawer,items) { scope,drawerState ->
        SimpleScreen(
            title = stringResource(R.string.game_name),
            navigationIcon = {
                IconButton(onClick = {
                    scope.launch { drawerState.open() }
                }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            },
            content = {
                SimpleCard {
                    AdaptiveLayout(
                        landscape = { LandscapeTitleScreen(onClickLobbies, btnResId) },
                        portrait = { PortraitTitleScreen(onClickLobbies, btnResId) }
                    )
                }
            }
        )
    }

}