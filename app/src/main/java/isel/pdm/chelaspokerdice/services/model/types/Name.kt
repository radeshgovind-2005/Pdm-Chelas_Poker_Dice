package isel.pdm.chelaspokerdice.services.model.types

@JvmInline
value class Name private constructor(val value: String) {
    companion object {
        fun create(value: String): Result<Name> =
            when {
                value.isBlank() -> Result.failure(IllegalArgumentException("Name cannot be blank"))
                else -> Result.success(Name(value.trim()))
            }
    }

    override fun toString(): String = value
}