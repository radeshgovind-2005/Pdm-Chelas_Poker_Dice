package isel.pdm.pokerdice.ui.activities.screens.title

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.buttons.ButtonText
import isel.pdm.pokerdice.ui.components.images.PokerDiceLogo
import isel.pdm.pokerdice.ui.components.layout.OneFullColumn
import isel.pdm.pokerdice.ui.components.layout.OneFullRow
import isel.pdm.pokerdice.ui.remember.RememberString

const val VERTICAL_SPACE = 100
@Composable
fun TitlePortraitContent(navToLobbies: () -> Unit){
    OneFullColumn {
        PokerDiceLogo()
        Spacer(Modifier.padding(VERTICAL_SPACE.dp))
        ButtonText(RememberString(R.string.bt_lobbies)){navToLobbies()}
    }
}

@Composable
fun TitleLandscapeContent(padding: PaddingValues, navToLobbies: () -> Unit){
    OneFullRow (
        { PokerDiceLogo() },
        { ButtonText(RememberString(R.string.bt_lobbies)){navToLobbies()} },
        paddingValues = padding
    )
}

