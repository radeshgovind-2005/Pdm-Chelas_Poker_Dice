package isel.pdm.pokerdice.domain.values

@JvmInline
value class ExpectedPlayers private constructor(val value: Int) {
    companion object {
        private const val MIN_PLAYERS = 2
        private const val MAX_PLAYERS = 6

        fun create(value: Int): Result<ExpectedPlayers>  =
            value
                .isValidExpectedPlayers()
                ?.let { Result.failure(it) }
                ?: Result.success(ExpectedPlayers(value))

        val min: Int get() = MIN_PLAYERS
        val max: Int get() = MAX_PLAYERS
    }

    override fun toString(): String = value.toString()
}

fun Int.isValidExpectedPlayers(): Throwable? =
    when {
        this < ExpectedPlayers.min -> TooFewPlayersException
        this > ExpectedPlayers.max -> TooManyPlayersException
        else -> null
    }

sealed class ExpectedPlayersException(msg: String) : Exception(msg)
data object TooFewPlayersException : ExpectedPlayersException("Expected players must be at least ${ExpectedPlayers.min}")
data object TooManyPlayersException : ExpectedPlayersException("Expected players cannot exceed ${ExpectedPlayers.max}")