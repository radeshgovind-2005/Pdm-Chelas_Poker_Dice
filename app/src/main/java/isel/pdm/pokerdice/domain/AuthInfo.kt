package isel.pdm.pokerdice.domain


import android.os.Parcelable
import isel.pdm.pokerdice.domain.values.Name
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthInfo(val userName: Name, val authToken: String): Parcelable