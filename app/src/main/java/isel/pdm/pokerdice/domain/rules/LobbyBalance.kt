package isel.pdm.pokerdice.domain.rules

@JvmInline
value class LobbyBalance(val value: Int) {
    init {
        require(isValid(value) == null) { "Invalid Balance" }
    }

    companion object {
        fun isValid(input: Int): String? {
            if (input <= 0) return "Initial balance must be greater than 0."
            return null
        }
    }
}