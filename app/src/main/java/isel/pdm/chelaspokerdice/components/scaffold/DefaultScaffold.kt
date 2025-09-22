package isel.pdm.chelaspokerdice.components.scaffold


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import isel.pdm.chelaspokerdice.components.background.BackgroundImage
import isel.pdm.chelaspokerdice.components.topbar.DefaultTopBar

@Composable
fun DefaultScaffold(titleName: String, icon: ImageVector? = null, code: @Composable () -> Unit) {
    val myIcon = icon ?: Icons.AutoMirrored.Filled.ArrowBack
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { DefaultTopBar(titleName, myIcon) },
    ) { innerPadding ->
        BackgroundImage()
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            code()
        }
    }
}


@PreviewScreenSizes
@Composable
private fun Preview() {
    DefaultScaffold("Radesh") { }
}


