package isel.pdm.pokerdice.domain.types

@JvmInline
value class Username(val value: String) {
    init {
        require(isValid(value) == null) { "Invalid Username" }
    }

    companion object {
        fun isValid(input: String): String? {
            if (input.isBlank()) return "Username cannot be empty."
            if (!input.first().isUpperCase()) return "Must start with a Capital letter."
            if (input.any { it.isDigit() }) return "Numbers are not allowed."
            if (!input.all { it.isLetter() }) return "Only alphabet letters allowed."
            return null
        }
    }
}