package isel.pdm.pokerdice.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val  MATCH_MAX_TRIES = 3
@Parcelize
data class Match(
    val players: Players,
    val turn: Player? = null,
    val tries:Int = 0,
    val round: Int = 1,
    val prize: Int = 0,
    val isActive: Boolean
): Parcelable {
    companion object{

        fun create(lobby: Lobby):Result<Match> =
            runCatching {
                val players = lobby.lobbyUsers.toPlayers()
                val prize = lobby.ante * players.size
                Match(
                    players=players,
                    turn=players[0],
                    prize=prize,
                    isActive=true
                )
            }
    }
}