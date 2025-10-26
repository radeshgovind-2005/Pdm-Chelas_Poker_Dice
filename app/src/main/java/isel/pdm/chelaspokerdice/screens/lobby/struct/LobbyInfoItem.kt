package isel.pdm.chelaspokerdice.screens.lobby.struct

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import isel.pdm.chelaspokerdice.ui.components.elements.LargeText
import isel.pdm.chelaspokerdice.ui.components.elements.SmallText

@Composable
 fun LobbyInfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SmallText(label)
        LargeText(value)
    }
}