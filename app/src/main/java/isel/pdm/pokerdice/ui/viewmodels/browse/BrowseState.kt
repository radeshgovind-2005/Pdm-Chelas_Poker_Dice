package isel.pdm.pokerdice.ui.viewmodels.browse

import isel.pdm.pokerdice.domain.model.lobby.BrowseLobby

data class BrowseState(
    val isLoading: Boolean=false,
    val query: String = "",
    val expanded: Boolean = false,
    val searchResults: List<String> = emptyList(),
    val lobbies: List<BrowseLobby> = emptyList(),
    val filteredLobbies: List<BrowseLobby> = emptyList(),
    val error: String? = null
)