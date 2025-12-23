package isel.pdm.pokerdice.domain.rules

@JvmInline
value class LobbyAnte(val value: Int) {
    init {
        require(isValid(value) == null) { "Invalid Ante" }
    }

    companion object {
        fun isValid(input: Int): String? {
            if (input <= 0) return "Ante must be greater than 0."
            return null
        }
    }
}