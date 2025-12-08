package isel.pdm.pokerdice.domain.types

@JvmInline
value class LobbyDescription(val value: String) {
    init {
        require(isValid(value) == null) { "Invalid Description" }
    }

    companion object {
        fun isValid(input: String): String? {
            if (input.isBlank()) return "Description cannot be empty."
            if (!input.first().isUpperCase()) return "Must start with a Capital letter."
            if (input.length > 50) return "Description is too long (max 50 chars)."
            return null
        }
    }
}