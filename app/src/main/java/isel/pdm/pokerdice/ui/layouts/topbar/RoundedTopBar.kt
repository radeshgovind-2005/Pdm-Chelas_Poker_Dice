package isel.pdm.pokerdice.ui.layouts.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import isel.pdm.pokerdice.ui.components.text.BoldTitle
import isel.pdm.pokerdice.ui.layouts.surfaces.RoundedBarSurface
import isel.pdm.pokerdice.ui.theme.PokerBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RounderTopBar(title: String,navigationIcon: @Composable ()-> Unit){
    RoundedBarSurface {
        CenterAlignedTopAppBar(
            modifier = Modifier.fillMaxSize(),
            title= { BoldTitle(title) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor=PokerBlack,
                titleContentColor=Color.White
            ),
            navigationIcon=navigationIcon
        )
    }
}