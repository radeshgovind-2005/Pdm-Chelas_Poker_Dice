package isel.pdm.pokerdice.services.fake

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import isel.pdm.pokerdice.data.FakeDataGenerator
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.services.LobbyServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.util.UUID

class FakeLobbyService(): LobbyServices{

    private val delayTime: Long
        get() = (2000..5000).random().toLong()

    override val lobbies: MutableState<Lobbies> =
        mutableStateOf(FakeDataGenerator.generateLobbies())

    override fun getWaitingLobbies(): Flow<Lobbies> =
        snapshotFlow { lobbies.value }
            .onStart { delay(delayTime) }
            .map{ list ->
                list.filter { isLobbyWaiting(it) }
            }

    override fun getLobbies(search: String): Flow<Lobbies> =
        snapshotFlow { lobbies.value }
            .onStart { delay(delayTime) }
            .map{ list ->
                list.filter { it.name.value.contains(search, ignoreCase = true)}
            }

    override fun leaveLobby(user: User, lobby: Lobby): Flow<Unit> =
        flow {
            lobbies
                .value
                .find{ it.id == lobby.id }
                ?.lobbyUsers
                ?.removeIf{ it.userCredentials.username == user.userCredentials.username }
                .apply { emit(Unit) }
        }

    override fun getUserLobby(user: User): Flow<Lobby?> =
        snapshotFlow { lobbies.value }
            .map { list ->
                list.find { it.lobbyUsers.contains(user) }
            }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun getAndJoinOnLobby(uuid: UUID, user: User): Flow<Lobby?> =
        flow {
            delay(delayTime)
            lobbies
                .value
                .find { lobby -> lobby.id == uuid }
                .apply{
                    if (this?.lobbyUsers
                            ?.none{ it.userCredentials.username == user.userCredentials.username }
                            ?: false
                    ) { lobbyUsers.addFirst(user) }
                    emit(this)
                }
        }

    override fun insertLobby(lobby: Lobby): Flow<Lobby?> =
        flow { lobbies.value += lobby; emit(lobby) }

    private fun isLobbyWaiting(lobby: Lobby): Boolean = lobby.lobbyUsers.size < lobby.expectedPlayers.value
}