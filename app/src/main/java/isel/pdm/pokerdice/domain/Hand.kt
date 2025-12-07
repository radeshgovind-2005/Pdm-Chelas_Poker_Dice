package isel.pdm.pokerdice.domain

data class Hand(
    val nDices: Int = 5,
    val value: List<ChelasPokerDice> = List(nDices) { ChelasPokerDice() }
) {
    fun updateHand(newHandStr: String): Hand {
        val newList = value.mapIndexed { idx, dice ->
            val charChar = if (idx < newHandStr.length) newHandStr[idx] else '?'
            dice.copy(
                face = DiceFaces.fromChar(charChar),
                isSelected = false
            )
        }
        return this.copy(value = newList)
    }

    fun toggleSelection(idx: Int): Hand {
        val newList = value.mapIndexed { index, dice ->
            if (index == idx) {
                dice.copy(isSelected = !dice.isSelected)
            } else {
                dice
            }
        }
        return this.copy(value = newList)
    }

    data class ChelasPokerDice(
        val face: DiceFaces = DiceFaces.ACE,
        val isSelected: Boolean = false
    )

    enum class DiceFaces(val symb: String){
        ACE("A"),
        KING("K"),
        QUEEN("Q"),
        JACK("J"),
        TEN("10"),
        NINE("9"),
        UNKNOWN("?");

        companion object {
            fun fromChar(char: Char): DiceFaces = when (char) {
                'A' -> ACE
                'K' -> KING
                'Q' -> QUEEN
                'J' -> JACK
                'T' -> TEN
                'N' -> NINE
                else -> UNKNOWN
            }
        }
    }
}