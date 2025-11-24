package isel.pdm.pokerdice.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Hand: Parcelable {

    val NUMBER_OF_DICES = 5
    var hand = List(NUMBER_OF_DICES){Face.NINE}
        private set

    fun rolling(){
        hand.map{ Face.entries.random()}
    }
    enum class Face(
        val symbol: Char,
        val rank: Int,
    ) {
        ACE('A', 6),
        KING('K', 5),
        QUEEN('Q', 4),
        JACK('J', 3),
        TEN('T', 2),
        NINE('9', 1);


    }
    override fun toString(): String = hand.map { it.symbol }.joinToString()
}