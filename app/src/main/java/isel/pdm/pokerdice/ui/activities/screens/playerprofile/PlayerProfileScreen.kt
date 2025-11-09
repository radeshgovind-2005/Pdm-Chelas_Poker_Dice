package isel.pdm.pokerdice.ui.activities.screens.playerprofile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun PlayerProfileScreen(
    navBack: () -> Unit = {},
    logout: () -> Unit = {},
) {
    CommonLayout(navBack,logout){

    }
}

@Composable
private fun CommonLayout(navBack: () -> Unit, logout: () -> Unit,content: @Composable (PaddingValues) -> Unit){
    DefaultLayout(
        topbar = {
            DefaultTopBar(
                title= RememberString(R.string.player_profile_title),
                navIcon = MyIcon.Back,
                onClick = navBack
            )
        },
        floatingBtn = {
            FloatingActionButton(
                onClick = logout,
                containerColor = Color.Red
            ) { SimpleIcon(MyIcon.Logout)}

        }
    ){ innerPadding ->
        content(innerPadding)
    }
}
