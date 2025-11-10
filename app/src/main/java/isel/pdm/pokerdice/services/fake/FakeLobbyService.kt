package isel.pdm.pokerdice.services.fake

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import isel.pdm.pokerdice.data.FakeDataGenerator
import isel.pdm.pokerdice.domain.Lobbies
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.services.LobbyServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLobbyService(): LobbyServices{

    override val lobbies: MutableState<Lobbies> =
        mutableStateOf(FakeDataGenerator.generateLobbies())

    override fun getWaitingLobbies(): Flow<Lobbies> =
        flow {
            delay(5000)
            lobbies
                .value
                .filter { isLobbyWaiting(it) }
                .apply{emit(this)}
        }

    override fun getLobbies(search: String): Flow<Lobbies> =
        flow {
            delay(5000)
            lobbies
                .value
                .filter { it.name.value.contains(search)}
                .apply{emit(this)}
        }

    override fun leaveLobby(user: User, lobby: Lobby): Flow<Unit> =
        flow {
            lobbies
                .value
                .find{ it.id == lobby.id }
                ?.lobbyPlayers
                ?.removeIf{ it.userCredentials.username == user.userCredentials.username }
                .apply { emit(Unit) }
        }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun getAndJoinOnLobby(id: String, user: User): Flow<Lobby?> =
        flow {
            delay(1000)
            lobbies
                .value
                .find { lobby -> lobby.id.toString() == id }
                .apply{ this?.lobbyPlayers?.addFirst(user) ;emit(this)}
        }

    override fun insertLobby(lobby: Lobby): Flow<Lobby?> =
        flow {
            lobbies.value += lobby
            emit(
                lobbies.value.firstOrNull{it.id === lobby.id}
            )
        }

    private fun isLobbyWaiting(lobby: Lobby): Boolean = lobby.lobbyPlayers.size < lobby.expectedPlayers.value
}