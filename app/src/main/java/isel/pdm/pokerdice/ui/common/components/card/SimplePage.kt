package isel.pdm.pokerdice.ui.common.components.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import isel.pdm.pokerdice.ui.common.components.text.BoldTitle
import isel.pdm.pokerdice.ui.common.components.text.TitleSize

@Composable
fun SimplePage(titleResId: Int, content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize(0.9f).verticalScroll(scrollState)
        ) {
            Row(
                modifier=Modifier.fillMaxWidth()
            ){
                BoldTitle(stringResource(titleResId), TitleSize.LARGE)
            }
            content()
        }
    }
}