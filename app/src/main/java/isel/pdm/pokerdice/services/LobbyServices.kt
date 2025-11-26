package isel.pdm.pokerdice.services

import androidx.compose.runtime.MutableState
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface LobbyServices {
    val lobbies: MutableState<Lobbies>
    fun getWaitingLobbies(): Flow<Lobbies>
    fun getAndJoinOnLobby(uuid: UUID, user: User): Flow<Lobby?>
    fun insertLobby(lobby: Lobby): Flow<Lobby?>
    fun getLobbies(serach: String): Flow<Lobbies>

    fun leaveLobby(user: User,lobby: Lobby): Flow<Unit>

    fun getUserLobby(user: User) : Flow<Lobby?>
}