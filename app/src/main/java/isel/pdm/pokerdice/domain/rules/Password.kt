package isel.pdm.pokerdice.domain.rules

@JvmInline
value class Password(val value: String) {
    init {
        require(isValid(value) == null) { "Invalid Password" }
    }

    companion object {
        fun isValid(input: String): String? {
            if (input.length < 4) return "Password must be at least 4 characters."
            if (input.isBlank()) return "Password cannot be Blank."
            if (input.none{it.isUpperCase()}) return "Password must have at least one Capital letter."
            if (input.none{ it.isDigit() }) return "Password must have at least one Number."
            return null
        }
    }
}