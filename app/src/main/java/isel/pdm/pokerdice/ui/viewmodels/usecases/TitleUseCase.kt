package isel.pdm.pokerdice.ui.viewmodels.usecases

import isel.pdm.pokerdice.domain.user.UserState
import isel.pdm.pokerdice.repo.AuthRepository
import isel.pdm.pokerdice.services.LobbyService
import isel.pdm.pokerdice.services.MatchService

class TitleUseCase(
    private val authRepo: AuthRepository,
    private val lobbyService: LobbyService,
) {

}