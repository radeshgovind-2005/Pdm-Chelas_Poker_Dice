package isel.pdm.pokerdice.ui.activities.screens.about

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.remember.RememberString
import isel.pdm.pokerdice.ui.components.tabs.DefaultTabs
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar

@Composable
fun AboutScreen(
    navBack: () -> Unit = {},
    navToWeb: (String) -> Unit = {},
    navToMail: (List<String>, String) -> Unit = {_,_->},
) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    CommonLayout(navBack){ padding ->
        DefaultTabs(
            tabItems = AboutTabItems(navToWeb,navToMail),
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index ->  selectedTabIndex = index },
            padding = padding
        )
    }
}

@Composable
private fun CommonLayout(navBack: () -> Unit, content: @Composable (PaddingValues) -> Unit){
    DefaultLayout(
        topbar = {
            DefaultTopBar(
                title= RememberString(R.string.about_title),
                navIcon = MyIcon.Back,
                onClick = navBack
            )
        }
    ){ innerPadding ->
        content(innerPadding)
    }
}

@Composable
private fun AboutTabItems(
    navToWeb: (String) -> Unit,
    navToMail: (List<String>, String) -> Unit
): Map<String, (@Composable () -> Unit)> {
    val tb1 = RememberString(R.string.tab_gameplay)
    val tb2 = RememberString(R.string.tab_thisProject)
    return mapOf<String, (@Composable () -> Unit)>(
        tb1 to { AboutGameplayContent(tb1, navToWeb) },
        tb2 to { AboutThisProjectContent(tb2, navToMail) },
    )
}
