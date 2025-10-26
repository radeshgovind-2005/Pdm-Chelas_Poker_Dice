package isel.pdm.chelaspokerdice.ui.components.struct

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import isel.pdm.chelaspokerdice.vm.LobbyViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(lobbyViewModel: LobbyViewModel) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchedItems = remember { mutableStateListOf<String>() }

    SearchBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            lobbyViewModel.searchLobbies(text)
            searchedItems.add(text)
            active = false
            text = ""
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text("Search lobbies...") },
        leadingIcon = { Icon(Icons.Default.Search, "Search Icon") },
        trailingIcon = {
            if (active) {
                Icon(
                    Icons.Default.Close,
                    "Close Icon",
                    modifier = Modifier.clickable {
                        if(text.isNotEmpty())
                            text = ""
                        else
                            active = false
                    }
                )
            }
        },
    ) {
        searchedItems.forEach {
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Refresh,
                    "Search Again Icon",
                    Modifier.padding(horizontal = 10.dp)
                )
                Text(it)
            }
        }
    }
}