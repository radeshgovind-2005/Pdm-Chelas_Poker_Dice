package isel.pdm.pokerdice.services.http

import android.app.appsearch.SearchResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import isel.pdm.pokerdice.app.AppLog
import isel.pdm.pokerdice.domain.match.Match
import isel.pdm.pokerdice.domain.lobby.BrowseLobby
import isel.pdm.pokerdice.domain.lobby.Lobby
import isel.pdm.pokerdice.domain.user.UserState
import isel.pdm.pokerdice.services.LobbyService
import isel.pdm.pokerdice.services.events.LobbiesEvents
import isel.pdm.pokerdice.services.events.LobbyEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class HttpLobbyService(
    private val client: OkHttpClient,
    private val gson: Gson,
) : LobbyService {

    private val logger by lazy { AppLog(this::class.java.simpleName) }

    override fun subscribeToLobbies(): Flow<LobbiesEvents> {
        val request = Request.Builder()
            .url("${BASE_URL}lobbies/events")
            .header("Accept", "text/event-stream")
            .build()

        return client.sseFlow(request).map { sseEvent ->
            try {
                logger.i("SSE LOBBIES EVENT -> ${sseEvent.event}")
                when (sseEvent.event) {
                    "connected" -> {
                        LobbiesEvents.Connected(sseEvent.data)
                    }
                    "lobbies-init" -> {
                        val listType = object : TypeToken<List<LobbyDto>>() {}.type
                        val dtos = gson.fromJson<List<LobbyDto>>(sseEvent.data, listType)
                        LobbiesEvents.Init(dtos.map { it.toDomain() })
                    }
                    "lobby-non-available" -> {
                        val mapType = object : TypeToken<Map<String, String>>() {}.type
                        val data = gson.fromJson<Map<String, String>>(sseEvent.data, mapType)
                        logger.i("data -> $data")
                        val lobbyIdToRemove = data.values.firstOrNull()

                        if (lobbyIdToRemove != null) {
                            LobbiesEvents.Remove(lobbyIdToRemove)
                        } else {
                            LobbiesEvents.Error("Received removal event without ID")
                        }
                    }
                    "lobby-added" -> {
                        val dto = gson.fromJson(sseEvent.data, LobbyDto::class.java)
                        LobbiesEvents.Add(dto.toDomain())
                    }
                    "ping" -> LobbiesEvents.KeepAlive
                    else -> LobbiesEvents.Error("Unknown event type: ${sseEvent.event}")
                }
            } catch (e: Exception) {
                LobbiesEvents.Error("Failed to parse event ${sseEvent.event}: ${e.message}")
            }
        }
    }


    override fun subscribeToLobby(lobbyId: String): Flow<LobbyEvent> {
        val request = Request.Builder()
            .url("${BASE_URL}lobbies/$lobbyId/events")
            .header("Accept", "text/event-stream")
            .build()

        return client.sseFlow(request).map { sseEvent ->
            try {
                logger.v("SSE LOBBY EVENT -> ${sseEvent.event}")
                when (sseEvent.event) {
                    "connected" -> {
                        LobbyEvent.Connected(sseEvent.data)
                    }
                    "lobby-init"->{
                        val dto = gson.fromJson(sseEvent.data, LobbyInitDto::class.java)
                        LobbyEvent.CurrentLobby(dto.toDomain())
                    }
                    "player-joined" -> {
                        val dto = gson.fromJson(sseEvent.data, PlayerActionDto::class.java)
                        LobbyEvent.PlayerJoined(dto.username)
                    }
                    "player-left" -> {
                        val dto = gson.fromJson(sseEvent.data, PlayerActionDto::class.java)
                        LobbyEvent.PlayerLeft(dto.username)
                    }

                    "match-started"->{
                        val dto = gson.fromJson(sseEvent.data, MatchDto::class.java)
                        LobbyEvent.MatchInit(dto.toDomain())
                    }
                    "ping" -> LobbyEvent.KeepAlive
                    else -> LobbyEvent.Error("Unknown event type: ${sseEvent.event}")
                }
            } catch (e: Exception) {
                LobbyEvent.Error("Failed to parse event ${sseEvent.event}: ${e.message}")
            }
        }
    }

    override suspend fun createLobby(
        authToken: String,
        name: String,
        description: String,
        players: Int,
        rounds: Int,
        balance: Int,
        ante: Int
    ): String? {
        val requestPayload = CreateLobbyRequest(
            name = name,
            description = description,
            expectedPlayers = players,
            rounds = rounds,
            initialBalance = balance,
            ante = ante
        )

        val requestBody = gson.toJson(requestPayload).toRequestBody(JSON_MEDIA_TYPE)
        val request = Request.Builder()
            .url("${BASE_URL}lobbies")
            .header("Authorization", "Bearer $authToken")
            .post(requestBody)
            .build()

        val response = client.newCall(request).await()
        if (!response.isSuccessful) {
            throw Exception("Failed to create lobby: ${response.code}")
        }
        val responseBody = response.body?.string() ?: throw Exception("Empty response")
        val responseDto = gson.fromJson(responseBody, DefaultLobbyResponse::class.java)
        return responseDto.lobbyId
    }

    override suspend fun joinLobby(lobbyId: String, token: String): String? {
        val emptyBody = "{}".toRequestBody(JSON_MEDIA_TYPE)

        val request = Request.Builder()
            .url("${BASE_URL}lobbies/join/$lobbyId")
            .header("Authorization", "Bearer $token")
            .post(emptyBody)
            .build()

        val response = client.newCall(request).await()

        if (!response.isSuccessful) {
            if (response.code == 409) throw Exception("Lobby is full")
            throw Exception("Failed to join lobby: ${response.code}")
        }

        val responseBody = response.body?.string() ?: throw Exception("Empty response")
        val responseDto = gson.fromJson(responseBody, DefaultLobbyResponse::class.java)
        return responseDto.lobbyId
    }

    override suspend fun leaveLobby(lobbyId: String, token: String): String? {
        val emptyBody = "{}".toRequestBody(JSON_MEDIA_TYPE)

        val request = Request.Builder()
            .url("${BASE_URL}lobbies/leave/$lobbyId")
            .header("Authorization", "Bearer $token")
            .put(emptyBody)
            .build()

        val response = client.newCall(request).await()

        if (!response.isSuccessful) {
            throw Exception("Failed to leave lobby: ${response.code}")
        }

        val responseBody = response.body?.string() ?: throw Exception("Empty response")
        val responseDto = gson.fromJson(responseBody, DefaultLobbyResponse::class.java)
        return responseDto.lobbyId
    }

    override suspend fun checkUserState(token: String): UserState? {
        val request = Request.Builder()
            .url("${BASE_URL}lobbies/check")
            .header("Authorization", "Bearer $token")
            .get()
            .build()
        val response = client.newCall(request).await()
        if (!response.isSuccessful) {
            return null
        }
        val responseBody = response.body?.string() ?: return null

        try {
            val dto = gson.fromJson(responseBody, CheckStateDto::class.java)
            if (dto.id == null || dto.status == null) {
                return null
            }
            return UserState(dto.status, dto.id)
        } catch (e: Exception) {
            logger.e("Failed to parse check state response: ${e.message}")
            return null
        }
    }

    private data class CreateLobbyRequest(
        val name: String,
        val description: String,
        val expectedPlayers: Int,
        val rounds: Int,
        val initialBalance: Int,
        val ante: Int
    )
    private data class DefaultLobbyResponse(
        val lobbyId: String
    )

    data class LobbyDto(
        val id: String,
        val name: String,
        val hostName: String,
        val maxRounds: Int,
    ){
        fun toDomain(): BrowseLobby{
            return BrowseLobby(
                id = id,
                name = name,
                hostName=hostName,
                rounds = maxRounds
            )
        }
    }

    private data class PlayerActionDto(
        val username: String
    )
    data class LobbyInitDto(
        val id: String,
        val name: String,
        val description: String,
        val host: String,
        val state: String,
        val players: List<String>,
        val maxRounds: Int,
        val maxPlayers: Int,
        val ante: Int,
        val initialBalance: Int,
    ){
        fun toDomain(): Lobby {
            return Lobby(
                id = id,
                name = name,
                description = description,
                host = host,
                state = state,
                maxRounds = maxRounds,
                maxPlayers = maxPlayers,
                ante = ante,
                initialBalance = initialBalance,
                players = players
            )
        }
    }

    data class MatchDto(val matchId: String){
        fun toDomain() : String = matchId
    }

    private data class CheckStateDto(
        val status: String?, // "waiting" ou "playing"
        val id: String?      // UUID do Lobby ou da Match
    )
}