package isel.pdm.chelaspokerdice.services.dto.types

import kotlin.math.min

@JvmInline
value class NumberOfRounds private constructor(val value: Int) {
    companion object {
        private const val MAX_ROUNDS = 60

        fun create(value: Int, expectedPlayers: ExpectedPlayers): Result<NumberOfRounds> {
            val maxPossibleRounds = min(MAX_ROUNDS, value)

            return when {
                value <= 0 ->
                    Result.failure(IllegalArgumentException("Number of rounds must be positive"))
                value % expectedPlayers.value != 0 ->
                    Result.failure(IllegalArgumentException("Number of rounds must be a multiple of expected players (${expectedPlayers.value})"))
                value > MAX_ROUNDS -> Result.failure(IllegalArgumentException("Number of rounds cannot exceed $MAX_ROUNDS"))
                else -> Result.success(NumberOfRounds(value))
            }
        }
    }

    val max: Int get() = MAX_ROUNDS

    override fun toString(): String = value.toString()
}