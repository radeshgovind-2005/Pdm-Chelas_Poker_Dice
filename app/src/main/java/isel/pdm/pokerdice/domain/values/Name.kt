package isel.pdm.pokerdice.domain.values

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Name private constructor(val value: String): Parcelable {
    companion object {
        fun create(value: String): Result<Name> =
            value
                .isValidName()
                ?.let{Result.failure(it)}
                ?: Result.success(Name(value.trim()))
    }

    override fun toString(): String = value
}

fun String.isValidName(): Throwable? =
    when {
    isBlank() -> BlankNameException
    any{it.isDigit()} -> DigitNameException
    else -> null
}

sealed class NameException(msg: String) : Exception(msg)
data object BlankNameException: NameException("Name cannot be blank")
data object DigitNameException: NameException("Name cannot have any digit")