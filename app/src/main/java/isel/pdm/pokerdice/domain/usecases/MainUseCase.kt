package isel.pdm.pokerdice.domain.usecases

import isel.pdm.pokerdice.domain.model.user.SessionInfo
import isel.pdm.pokerdice.domain.model.user.User
import isel.pdm.pokerdice.domain.model.user.UserState
import isel.pdm.pokerdice.domain.repo.AuthRepository
import isel.pdm.pokerdice.domain.services.AuthService
import isel.pdm.pokerdice.domain.services.LobbyService

class UserNotAuthenticated: Exception("User is not Authenticated.")
class MainUseCase(
    private val authRepo: AuthRepository,
    private val authService: AuthService,
    private val lobbyService: LobbyService,
) {

    suspend fun sessionCheck(): Result<Pair<User, SessionInfo>> =
        runCatching {
            val info = authRepo.getAuthInfo()
            if(info == null) throw UserNotAuthenticated()
            val token = info.authToken
            if(token==null)throw UserNotAuthenticated()
            val sessionInfo = authService.sessionCheck(token)
            Pair(User(info), sessionInfo)
        }

    suspend fun checkPlayerState(): Result<UserState?> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null ) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token==null ) throw Exception("Session Expired")
            val state =lobbyService.checkUserState(token)
            if(state==null) throw Exception("error checking player state")
            state
        }
}