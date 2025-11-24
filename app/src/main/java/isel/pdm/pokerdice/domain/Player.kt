package isel.pdm.pokerdice.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Player(
    val id: UUID = UUID.randomUUID(),
    val user: User,
    val hand: Hand? = null
): Parcelable

typealias Players = List<Player>