package isel.pdm.pokerdice.domain.values

import kotlin.math.min

@JvmInline
value class NumberOfRounds private constructor(val value: Int) {
    companion object {
        private const val MAX_ROUNDS = 60

        fun create(value: Int, expectedPlayers: Int): Result<NumberOfRounds> =
            value
                .isValidNumberOfRounds(expectedPlayers)
                ?.let { Result.failure(it) }
                ?: Result.success(NumberOfRounds(value))

        val max: Int get() = MAX_ROUNDS
    }

    val max: Int get() = MAX_ROUNDS

    override fun toString(): String = value.toString()
}
fun Int.isValidNumberOfRounds(expectedPlayers: Int): Throwable? {
    return when {
        this <= 0 -> NonPositiveRoundsException
        this % expectedPlayers != 0 -> InvalidMultipleRoundsException(expectedPlayers)
        this > NumberOfRounds.max -> TooManyRoundsException
        else -> null
    }
}

sealed class NumberOfRoundsException(msg: String) : Exception(msg)
data object NonPositiveRoundsException : NumberOfRoundsException("Number of rounds must be positive")
data class InvalidMultipleRoundsException(val expectedPlayers: Int) :
    NumberOfRoundsException("Number of rounds must be a multiple of expected players ($expectedPlayers)")
data object TooManyRoundsException : NumberOfRoundsException("Number of rounds cannot exceed ${NumberOfRounds.max}")