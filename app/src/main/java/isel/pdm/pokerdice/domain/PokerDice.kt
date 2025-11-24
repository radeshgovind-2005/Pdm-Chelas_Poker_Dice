package isel.pdm.pokerdice.domain

const val NUMBER_OF_DICES = 5


data class PokerHand(
    val dices: List<Dice> =
        List(NUMBER_OF_DICES) {Dice(Face.NINE)}
){
    fun roll(): PokerHand {
        val newDiceList = dices.map { dice -> dice.reroll() }
        return PokerHand(newDiceList)
    }

    fun toggleSelection(index: Int): PokerHand {
        if (index !in dices.indices) return this

        val newDiceList = dices.toMutableList()
        newDiceList[index] = newDiceList[index].toggleSelection()

        return PokerHand(newDiceList)
    }
}

data class Dice(
    val face: Face,
    val isSelected: Boolean = false
){
    fun toggleSelection(): Dice = copy(isSelected=!isSelected)
    fun reroll():Dice=if(isSelected)this else copy(Face.random())
}

enum class Face(val symb: Char, val value: Int) {
    NINE('9', 1),
    TEN('T', 2),
    JACK('J', 3),
    QUEEN('Q', 4),
    KING('K', 5),
    ACE('A', 6);

    companion object {
        fun random() = entries.random()
    }
}