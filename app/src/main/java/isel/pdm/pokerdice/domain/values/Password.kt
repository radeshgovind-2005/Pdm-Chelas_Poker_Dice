package isel.pdm.pokerdice.domain.values

@JvmInline
value class Password private constructor(val value: String) {
    companion object {
        fun create(value: String): Result<Password> =
            value
                .isValidPassword()
                ?.let{Result.failure(it)}
                ?: Result.success(Password(value))
    }

    override fun toString(): String = value
}

fun String.isValidPassword(): Throwable? =
    when {
        isBlank() -> BlankPasswordException
        none{it.isDigit()} -> DigitPasswordException
        none{it.isUpperCase()} -> CapitalLetterPasswordException
        none{it.isLowerCase()} -> SmallLetterPasswordException
        else -> null
    }

sealed class PasswordException(msg: String) : Exception(msg)
data object BlankPasswordException: PasswordException("Password cannot be blank")
data object DigitPasswordException: PasswordException("Password must have a digit")
data object CapitalLetterPasswordException: PasswordException("Password must have a Capital Letter")
data object SmallLetterPasswordException: PasswordException("Password must have a Lower Letter")