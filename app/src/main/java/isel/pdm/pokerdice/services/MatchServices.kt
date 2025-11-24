package isel.pdm.pokerdice.services

import isel.pdm.pokerdice.domain.Match
import isel.pdm.pokerdice.domain.Lobby
import isel.pdm.pokerdice.domain.User
import kotlinx.coroutines.flow.Flow

interface MatchServices {
    fun getMatch(user: User, lobby: Lobby, isActive: Boolean=false): Flow<Match?>

    fun insertMatch(match: Match): Flow<Match?>
}