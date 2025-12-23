package isel.pdm.pokerdice.domain.usecases

import isel.pdm.pokerdice.domain.events.LobbyEvent
import isel.pdm.pokerdice.domain.repo.AuthRepository
import isel.pdm.pokerdice.domain.services.LobbyService
import isel.pdm.pokerdice.domain.services.MatchService
import kotlinx.coroutines.flow.Flow

class LobbyUseCase(
    private val matchService: MatchService,
    private val lobbyService: LobbyService,
    private val authRepo: AuthRepository
) {

    suspend fun startMatch(lobbyId: String): Result<String> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null ) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token==null ) throw Exception("Session Expired")
            val matchId = matchService.startMatch(lobbyId,token)
            if(matchId==null) throw Exception("matchid not provided")
            matchId
        }
    suspend fun getUsername(): String{
        return authRepo.getAuthInfo()?.username?.value ?: ""
    }
    fun subscribeToLobby(lobbyId: String): Flow<LobbyEvent>  {
        return lobbyService.subscribeToLobby(lobbyId)
    }

    suspend fun joinLobby(lobbyId: String): Result<String> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null ) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token==null ) throw Exception("Session Expired")
            val lobbyid =lobbyService.joinLobby(lobbyId,token)
            if(lobbyid==null) throw Exception("lobbyid not provided")
            lobbyid
        }

    suspend fun leaveLobby(lobbyId: String): Result<String> =
        runCatching {
            if(lobbyId=="") return Result.success("")
            val auth = authRepo.getAuthInfo()
            if(auth==null ) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token==null ) throw Exception("Session Expired")
            val id = lobbyService.leaveLobby(lobbyId,token)
            if(id==null) throw Exception("lobbyid not provided")
            id
        }
}