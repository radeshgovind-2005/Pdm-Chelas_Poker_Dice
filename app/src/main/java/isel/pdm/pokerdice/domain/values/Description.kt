package isel.pdm.pokerdice.domain.values

@JvmInline
value class Description private constructor(val value: String) {
    companion object {
        fun create(value: String): Result<Description>  =
            value
                .isValidDescription()
                ?.let { Result.failure(it) }
                ?: Result.success(Description(value))
    }

    override fun toString(): String = value
}
fun String.isValidDescription(): Throwable? =
    when {
        isBlank() -> BlankDescriptionException
        else -> null
    }

sealed class DescriptionException(msg: String) : Exception(msg)
data object BlankDescriptionException : DescriptionException("Description cannot be blank")