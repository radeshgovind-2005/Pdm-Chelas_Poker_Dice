package isel.pdm.pokerdice.ui.components.drawer

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.ui.components.text.BoldTitle
import isel.pdm.pokerdice.ui.theme.PokerBlack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

 open class DrawerItem(val icon: ImageVector, open val resId: Int, open val onClick: () -> Unit)
    data class Info(override val resId: Int, override val onClick: () -> Unit): DrawerItem(Icons.Default.Info,resId,onClick)
    data class Person(override val resId: Int, override val onClick: () -> Unit): DrawerItem(Icons.Default.Person,resId,onClick)

@Composable
fun SimpleDrawer(
    titleResId: Int,
    dItems: List<DrawerItem>,
    content: @Composable (CoroutineScope, DrawerState) -> Unit
){
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = PokerBlack
            ){
                NavigationDrawerItem(
                    label = { BoldTitle(stringResource(titleResId)) },
                    selected = false,
                    icon = { },
                    onClick={},
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                dItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(stringResource(item.resId)) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            item.onClick()
                        },
                        icon = { Icon(item.icon, contentDescription = null) },
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }
        }
    ) {
        content(scope,drawerState)
    }
}