package isel.pdm.pokerdice.ui.viewmodels.usecases

import isel.pdm.pokerdice.repo.AuthRepository
import isel.pdm.pokerdice.services.MatchService
import isel.pdm.pokerdice.services.events.MatchEvents
import kotlinx.coroutines.flow.Flow

class MatchUseCase(
    private val matchService: MatchService,
    private val authRepo: AuthRepository,
) {
    suspend fun getUsername(): Result<String> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null) throw Exception("User not authenticated")
            return@runCatching auth.username.value
        }

    suspend fun startRound(matchId: String): Result<Unit> =
        runCatching {
            matchService.startRound(matchId)
        }

    suspend fun completeRound(matchId: String): Result<Unit> =
        runCatching {
            matchService.completeRound(matchId)
        }

    fun subscribeToMatch(matchId: String): Flow<MatchEvents> {
        return matchService.subscribeToMatch(matchId)
    }

    suspend fun rollAllDices(matchId: String): Result<Unit> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token == null) throw Exception("User not logged in")
            return matchService.rollAllDices(matchId,token)
        }

    suspend fun holdDices(matchId: String): Result<Unit> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token == null) throw Exception("User not logged in")
            return matchService.holdDices(matchId,token)
        }

    suspend fun rollDices(matchId: String, dices: List<Int>): Result<Unit> =
        runCatching {
            val auth = authRepo.getAuthInfo()
            if(auth==null) throw Exception("User not authenticated")
            val token = auth.authToken
            if(token == null) throw Exception("User not logged in")
            return matchService.rollDices(matchId,dices,token)
        }
}