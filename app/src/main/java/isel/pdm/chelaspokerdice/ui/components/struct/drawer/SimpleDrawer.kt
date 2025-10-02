package isel.pdm.chelaspokerdice.ui.components.struct.drawer

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch

@Composable
fun SimpleDrawer(
    title: String,
    menuItems: Array<DrawerMenuItem>,
    content: @Composable (onOpenDrawer: () -> Unit) -> Unit,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemId by remember {
        mutableStateOf("")
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerTitle(title)
                menuItems.forEach { menuItem ->
                    DrawerItem(
                        label = menuItem.label,
                        selected = selectedItemId == menuItem.id
                    ) {
                        selectedItemId = menuItem.id
                        scope.launch { drawerState.close() }
                        menuItem.action()
                    }
                }
            }
        }
    ) {
        content { scope.launch { drawerState.open() } }
    }
}