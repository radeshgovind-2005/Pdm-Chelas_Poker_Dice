package isel.pdm.pokerdice.ui.activities.screens.session

import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.layout.DefaultLayout
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.topbar.DefaultTopBar
import isel.pdm.pokerdice.ui.remember.RememberString

@Composable
fun SessionExpiredScreen(onClick: () -> Unit) {
    DefaultLayout(
        topbar = { DefaultTopBar(RememberString(R.string.session_expired_title),) }
    ){ innerPadding ->
        OneFullColumn(innerPadding) {
            ButtonText(RememberString(R.string.session_expired_btn)){
                onClick()
            }
        }
    }
}
