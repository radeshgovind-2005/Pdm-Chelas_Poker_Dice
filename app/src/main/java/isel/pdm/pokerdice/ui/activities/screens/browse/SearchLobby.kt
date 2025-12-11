package isel.pdm.pokerdice.ui.activities.screens.browse

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.inputFieldColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import isel.pdm.pokerdice.R
import isel.pdm.pokerdice.ui.viewmodels.browse.BrowseState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
 fun SearchLobby(
    state: BrowseState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    SearchBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = state.query,
                onQueryChange = onQueryChange,
                onSearch = { onSearch() },
                expanded = state.expanded,
                onExpandedChange = { },
                placeholder = { Text(stringResource(R.string.browse_placeholder), color = MaterialTheme.colorScheme.onPrimary) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                trailingIcon = null,
                colors = inputFieldColors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                )
            )
        },
        expanded = false,
        onExpandedChange = {},
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.primary),
        content = {}
    )
}