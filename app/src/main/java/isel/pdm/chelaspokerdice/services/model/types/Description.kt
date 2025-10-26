package isel.pdm.chelaspokerdice.services.model.types

@JvmInline
value class Description private constructor(val value: String) {
    companion object {
        fun create(value: String): Result<Description> = when {
            value.isBlank() -> Result.failure(IllegalArgumentException("Description cannot be blank"))
            else -> Result.success(Description(value.trim()))
        }
    }

    override fun toString(): String = value
}