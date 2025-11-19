package isel.pdm.pokerdice.ui.activities.screens.game

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import isel.pdm.pokerdice.ui.activities.screens.game.elements.table.PokerTable

@Composable
fun GameMainLayout(
    upperRow: @Composable () -> Unit = {},
    bottomRow: @Composable () -> Unit = {},
    tableContent: @Composable (PaddingValues) -> Unit = {},
    screenContent: @Composable (PaddingValues) -> Unit = {},
) {
    Scaffold(
        topBar = {upperRow()},
        floatingActionButton = {bottomRow()}
    ) { paddingValues ->
        PokerTable{ tableContent(paddingValues)}
        screenContent(paddingValues)
    }
}