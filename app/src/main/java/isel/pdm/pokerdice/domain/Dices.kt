package isel.pdm.pokerdice.domain

class Dices {

    companion object{
        val nr_dices = 5

        private val faces = listOf(
            Faces.Nine,
            Faces.Ten,
            Faces.Jack,
            Faces.Queen,
            Faces.King,
            Faces.Ace,
        )
        val randomFace
            get() = faces.random()
    }

    val roundHand = List(nr_dices) { PokerDice() }

    data class PokerDice(val face: Faces = Faces.Nine, val isSelected: Boolean = false)

    sealed class Faces(val symb: String){
        data object Nine: Faces("9")
        data object Ten: Faces("9")
        data object Jack: Faces("9")
        data object Queen: Faces("9")
        data object King: Faces("9")
        data object Ace: Faces("9")
    }
}