package isel.pdm.pokerdice.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val userCredentials: UserCredentials,
    val authInfo: AuthInfo
): Parcelable

typealias Users = MutableList<User>

fun Users.toPlayers(): Players = map{Player(user=it)}