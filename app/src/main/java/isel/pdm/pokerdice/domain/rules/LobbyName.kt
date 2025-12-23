package isel.pdm.pokerdice.domain.rules

@JvmInline
value class LobbyName(val value: String) {
    init {
        require(isValid(value) == null) { "Invalid Lobby Name" }
    }

    companion object {
        fun isValid(input: String): String? {
            if (input.isBlank()) return "Lobby Name cannot be empty or blank."
            if (!input.first().isUpperCase()) return "Must start with a Capital letter."
            if (input.length > 20) return "Lobby Name must be 20 characters or fewer."
            return null
        }
    }
}