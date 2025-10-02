package isel.pdm.chelaspokerdice.ui.components.struct.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SimpleTabs(
    tabItems: Map<String, (@Composable () -> Unit)>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
    ) {
    //var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabKeys = tabItems.keys.toList()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment= Alignment.CenterHorizontally,
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier
                .fillMaxWidth(),
            containerColor = Color.Black.copy(alpha = 0.6f),
            contentColor = Color.White
        ) {
            tabKeys.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        tabItems[tabKeys[selectedTabIndex]]?.invoke()
    }
}