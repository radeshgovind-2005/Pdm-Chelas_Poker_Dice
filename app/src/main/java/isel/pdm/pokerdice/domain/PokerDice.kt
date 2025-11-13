package isel.pdm.pokerdice.domain


class PokerDice {
    private val nOfDices = 5
    var hand = Array(nOfDices) { Face.NINE }
        private set


    sealed class Hand(faces: Array<Face>){
        class FiveOfAKind(faces: Array<Face>) : Hand(faces)
        class FourOfAKind(faces: Array<Face>) : Hand(faces)
        class FullHouse(faces: Array<Face>) : Hand(faces)
        class Straight(faces: Array<Face>) : Hand(faces)
        class ThreeOfAKind(faces: Array<Face>) : Hand(faces)
        class TwoPairs(faces: Array<Face>) : Hand(faces)
        class OnePair(faces: Array<Face>) : Hand(faces)
        class HighCard(faces: Array<Face>) : Hand(faces)
    }

    sealed class Face(val symb: Char, val value: Int){

        companion object {
            val entries = listOf(NINE, TEN, JACK, QUEEN, KING, ACE)
        }

        data object NINE : Face('9', 1)
        data object TEN : Face('T', 2)
        data object JACK : Face('J', 3)
        data object QUEEN : Face('Q', 4)
        data object KING : Face('K', 5)
        data object ACE : Face('A', 6)
    }
}