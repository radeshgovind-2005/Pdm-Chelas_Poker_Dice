package isel.pdm.pokerdice.ui.components.searchbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.components.icons.MyIcon
import isel.pdm.pokerdice.ui.components.icons.SimpleIcon
import isel.pdm.pokerdice.ui.remember.RememberString


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultSearchbar(
    padding: PaddingValues,
    onSearch: (String) -> Unit,
    placeholder: String = RememberString(R.string.search_placeholder)
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val searchedItems = remember { mutableStateListOf<String>() }

    fun whenSearch(){
        onSearch(text)
        searchedItems.add(text)
        active = false
        text = ""
    }

    fun whenClose(){
        if(text.isNotEmpty())
            text = ""
        else
            active = false
    }

    SearchBar(
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,    
            top = padding.calculateTopPadding(),
            bottom = padding.calculateBottomPadding()
        ),
        query = text,
        onQueryChange = { text = it },
        onSearch = {whenSearch()},
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(placeholder) },
        leadingIcon = { SimpleIcon(MyIcon.Search) },
        trailingIcon = {
            if (active) {
                SimpleIcon(
                    icon = MyIcon.Close,
                    modifier = Modifier.clickable {whenClose()}
                )
            }
        },
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        searchedItems.forEach {
            Row(
                Modifier
                    .padding(16.dp)
                    .clickable{whenSearch()},
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleIcon(MyIcon.Last,10)
                Text(it)
            }
        }
    }
}