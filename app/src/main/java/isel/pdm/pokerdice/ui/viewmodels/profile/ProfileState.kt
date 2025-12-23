package isel.pdm.pokerdice.ui.viewmodels.profile

import isel.pdm.pokerdice.domain.model.user.UserStats

data class ProfileState(
    val isLoading: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val stats: UserStats? =null,
    val username: String = ""
)