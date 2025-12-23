package isel.pdm.pokerdice.data.services.http

import com.google.gson.Gson
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.events.MatchEvents
import isel.pdm.pokerdice.domain.model.match.Game
import isel.pdm.pokerdice.domain.model.match.LobbyContent
import isel.pdm.pokerdice.domain.model.match.MatchContent
import isel.pdm.pokerdice.domain.model.match.MatchPlayers
import isel.pdm.pokerdice.domain.model.match.Round
import isel.pdm.pokerdice.domain.services.MatchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class HttpMatchService(
    private val client: OkHttpClient,
    private val gson: Gson,
) : MatchService {
    private val logger by lazy { AppLog(this::class.java.simpleName) }

    override suspend fun startMatch(lobbyId: String, token: String): String? {
        val emptyBody = "{}".toRequestBody(JSON_MEDIA_TYPE)

        val request = Request.Builder()
            .url("${BASE_URL}match/lobby/$lobbyId")
            .header("Authorization", "Bearer $token")
            .post(emptyBody)
            .build()

        return client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                throw Exception("Failed to Start Match: ${response.code}")
            }
            val responseBody = response.body?.string() ?: throw Exception("Empty response")
            val responseDto = gson.fromJson(responseBody, MatchDto::class.java)
            responseDto.matchId
        }
    }

    override suspend fun getMatchInfo(matchId: String): Game {
        val request = Request.Builder()
            .url("${BASE_URL}match/info/$matchId")
            .get()
            .build()

        return client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Failed to get match info: ${response.code}")
            }
            val responseBody = response.body?.string() ?: throw IOException("Empty response")
            val dto = gson.fromJson(responseBody, MatchInfoDto::class.java)
            dto.toDomain()
        }
    }

    override suspend fun startRound(matchId: String) {
        val request = Request.Builder()
            .url("${BASE_URL}match/round/begin/$matchId")
            .post("{}".toRequestBody(JSON_MEDIA_TYPE))
            .build()

        client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Failed to start round: ${response.code}")
            }
        }
    }

    override suspend fun completeRound(matchId: String) {
        val request = Request.Builder()
            .url("${BASE_URL}match/round/complete/$matchId")
            .post("{}".toRequestBody(JSON_MEDIA_TYPE))
            .build()

        client.newCall(request).await().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Failed to complete round: ${response.code}")
            }
        }
    }

    override fun subscribeToMatch(matchId: String): Flow<MatchEvents> {
        val request = Request.Builder()
            .url("${BASE_URL}match/$matchId/events/subscribe")
            .header("Accept", "text/event-stream")
            .build()

        return client
            .sseFlow(request)
            .retry { cause ->
                logger.w("SSE connection lost: ${cause.message}. Retrying...")
                cause is Exception
            }
            .map { sseEvent ->
                try {
                    logger.i("SSE Match EVENT -> ${sseEvent.event} -> ${sseEvent.data}")
                    when (sseEvent.event) {
                        "connected" -> {
                            MatchEvents.Connected(sseEvent.data)
                        }
                        "subscribed" -> {
                            val wrapper = gson.fromJson(sseEvent.data, MatchEventWrapper::class.java)
                            MatchEvents.Subscribed(wrapper.value.toDomain())
                        }
                        "round-started" -> {
                            val dto = gson.fromJson(sseEvent.data, MatchInfoDto::class.java)
                            MatchEvents.RoundInit(dto.toDomain())
                        }
                        "round-ended" -> {
                            val dto = gson.fromJson(sseEvent.data, MatchInfoDto::class.java)
                            MatchEvents.RoundComplete(dto.toDomain())
                        }
                        "roll-dices" -> {
                            val dto = gson.fromJson(sseEvent.data, MatchInfoDto::class.java)
                            MatchEvents.RollAll(dto.toDomain())
                        }
                        "re-roll-dices" -> {
                            val dto = gson.fromJson(sseEvent.data, MatchInfoDto::class.java)
                            MatchEvents.ReRoll(dto.toDomain())
                        }
                        "hold-dices" -> {
                            val dto = gson.fromJson(sseEvent.data, MatchInfoDto::class.java)
                            MatchEvents.HoldAll(dto.toDomain())
                        }
                        "ping" -> MatchEvents.KeepAlive
                        else -> MatchEvents.Error("Unknown event type: ${sseEvent.event}")
                    }
                }catch (e: Exception) {
                    MatchEvents.Error("Failed to parse event ${sseEvent.event}: ${e.message}")
                }
            }
    }

    override suspend fun rollAllDices(
        matchId: String,
        token: String
    ): Result<Unit> {
        return try {
            val request = Request.Builder()
                .url("${BASE_URL}match/play/$matchId")
                .header("Authorization", "Bearer $token")
                .post("{}".toRequestBody(JSON_MEDIA_TYPE))
                .build()

            val response = client.newCall(request).await()

            if (!response.isSuccessful) {
                Result.failure(IOException("Failed to roll all dices. Code: ${response.code}"))
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun holdDices(
        matchId: String,
        token: String
    ): Result<Unit> {
        return try {
            val request = Request.Builder()
                .url("${BASE_URL}match/hold/$matchId")
                .header("Authorization", "Bearer $token")
                .post("{}".toRequestBody(JSON_MEDIA_TYPE))
                .build()

            val response = client.newCall(request).await()

            if (!response.isSuccessful) {
                Result.failure(IOException("Failed to hold dices. Code: ${response.code}"))
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rollDices(
        matchId: String,
        dices: List<Int>, // These are the indices/values to HOLD
        token: String
    ): Result<Unit> {
        return try {
            // Create the JSON body: { "holdDices": [2, 4] }
            val bodyPayload = ReRollRequest(holdDices = dices)
            val jsonBody = gson.toJson(bodyPayload)

            val request = Request.Builder()
                .url("${BASE_URL}match/play/again/$matchId")
                .header("Authorization", "Bearer $token")
                .post(jsonBody.toRequestBody(JSON_MEDIA_TYPE))
                .build()

            val response = client.newCall(request).await()

            if (!response.isSuccessful) {
                Result.failure(IOException("Failed to re-roll dices. Code: ${response.code}"))
            } else {
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    data class MatchDto(val matchId: String)
    data class MatchEventWrapper(val value: MatchInfoDto)
    private data class ReRollRequest(val holdDices: List<Int>)

    data class MatchInfoDto(
        val matchId: String,
        val matchStatus: String,
        val matchIsStarted: Boolean,
        val matchIsCompleted: Boolean,
        val lobbyId: String,
        val lobbyName: String,
        val ante: Int,
        val totalRounds: Int,
        val currRound: Int?,
        val roundBet: Int?,
        val players: List<MatchPlayers>?,
        val message: String?
    ){
        fun toDomain(): Game = Game (
            username = null,
            match = MatchContent(
                matchId = matchId,
                status = matchStatus,
                isStarted = matchIsStarted,
                isCompleted = matchIsCompleted
            ),
            lobby = LobbyContent(
                lobbyId = lobbyId,
                name = lobbyName,
                ante = ante
            ),
            round = if (currRound != null && roundBet != null && players != null) {
                Round(
                    totalRounds = totalRounds,
                    currRound = currRound,
                    roundBet = roundBet,
                    players = players.map { dto ->
                        MatchPlayers(
                            name = dto.name,
                            hand = dto.hand,
                            rank = dto.rank,
                            balance = dto.balance,
                            state = dto.state,
                            rerollsLeft = dto.rerollsLeft
                        )
                    }
                )
            } else null,
            msg = message
        )
    }
}