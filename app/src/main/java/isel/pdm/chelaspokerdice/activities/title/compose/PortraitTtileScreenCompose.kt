package isel.pdm.chelaspokerdice.activities.title.compose

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import isel.pdm.chelaspokerdice.components.scaffold.DefaultScaffold
import kotlinx.coroutines.launch


@Composable
fun PortraitTitleScreenCompose(
    code: @Composable () -> Unit
) {
    DefaultScaffold("Chelas Poker Dice", Icons.Filled.Menu, code = { code() })
}


@PreviewScreenSizes
@Composable
private fun Preview() {
    PortraitTitleScreenCompose { Button(onClick = {}) { Text("Ola") } }
}
