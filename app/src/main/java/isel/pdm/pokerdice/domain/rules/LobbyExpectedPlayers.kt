package isel.pdm.pokerdice.domain.rules

@JvmInline
value class LobbyExpectedPlayers(val value: Int) {
    init {
        require(isValid(value) == null) { "Invalid Player Count" }
    }

    companion object {
        const val MIN_PLAYERS = 2
        const val MAX_PLAYERS = 6

        fun isValid(input: Int): String? {
            if (input !in MIN_PLAYERS..MAX_PLAYERS)
                return "Expected players must be between $MIN_PLAYERS and $MAX_PLAYERS."
            return null
        }
    }
}