package isel.pdm.pokerdice.domain.rules

@JvmInline
value class LobbyRounds(val value: Int) {
    init {
        require(value in 1..MAX_ROUNDS) { "Invalid Round Count" }
    }

    companion object {
        const val MAX_ROUNDS = 60

        fun isValid(rounds: Int, playerCount: Int): String? {
            if (rounds <= 0) return "Rounds must be positive."
            if (rounds > MAX_ROUNDS) return "Max rounds is $MAX_ROUNDS."
            if (playerCount == 0) return "Player count cannot be zero."

            if (rounds % playerCount != 0) {
                return "Rounds ($rounds) must be a multiple of the player count ($playerCount)."
            }
            return null
        }
    }
}