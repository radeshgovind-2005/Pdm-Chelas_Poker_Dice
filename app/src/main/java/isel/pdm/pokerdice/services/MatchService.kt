package isel.pdm.pokerdice.services

import isel.pdm.pokerdice.domain.match.Game
import isel.pdm.pokerdice.services.events.MatchEvents
import kotlinx.coroutines.flow.Flow


interface MatchService {
    suspend fun startMatch(lobbyId: String, token: String): String?
    suspend fun getMatchInfo(matchId: String): Game
    suspend fun startRound(matchId: String): Unit
    suspend fun completeRound(matchId: String): Unit
     fun subscribeToMatch(matchId: String): Flow<MatchEvents>
    suspend fun rollAllDices(matchId: String, token: String): Result<Unit>
    suspend fun holdDices(matchId: String, token: String): Result<Unit>
    suspend fun rollDices(matchId: String, dices: List<Int>, token: String): Result<Unit>
}


