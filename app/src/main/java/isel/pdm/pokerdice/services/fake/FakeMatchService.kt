package isel.pdm.pokerdice.services.fake

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import isel.pdm.pokerdice.domain.Match
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import isel.pdm.pokerdice.services.MatchServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeMatchService() : MatchServices {

    private val delayTime: Long
        get() = (2000..5000).random().toLong()

    private val matches: MutableState<List<Match>> =
        mutableStateOf(emptyList())

    override fun getMatch(
        user: User,
        lobby: Lobby,
        isActive: Boolean
    ): Flow<Match?> =
        snapshotFlow { matches.value }
            .map{ list->
                list.find {
                    it.isActive==isActive
                            && it.players.any{it.user==user}
                }
            }

    override fun insertMatch(match: Match): Flow<Match?> =
        flow{
            matches.value += match
            emit(match)
        }
}