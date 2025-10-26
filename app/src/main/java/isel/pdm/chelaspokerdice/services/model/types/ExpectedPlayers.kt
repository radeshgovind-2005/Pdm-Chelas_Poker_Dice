package isel.pdm.chelaspokerdice.services.model.types

@JvmInline
value class ExpectedPlayers private constructor(val value: Int) {
    companion object {
        private const val MIN_PLAYERS = 2
        private const val MAX_PLAYERS = 6

        fun create(value: Int): Result<ExpectedPlayers> = when {
            value < MIN_PLAYERS -> Result.failure(IllegalArgumentException("Expected players must be at least $MIN_PLAYERS"))
            value > MAX_PLAYERS -> Result.failure(IllegalArgumentException("Expected players cannot exceed $MAX_PLAYERS"))
            else -> Result.success(ExpectedPlayers(value))
        }
    }

    val min: Int get() = MIN_PLAYERS
    val max: Int get() = MAX_PLAYERS

    override fun toString(): String = value.toString()
}
