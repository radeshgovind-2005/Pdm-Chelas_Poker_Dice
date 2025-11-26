package isel.pdm.pokerdice.ui.activities

import android.app.Activity
import android.os.Build
import android.os.Parcelable
import java.io.Serializable
import java.util.UUID

// Helper to get a Parcelable (Handles the Android 13 / Tiramisu breaking change)
inline fun <reified T : Parcelable> Activity.extraParcelable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        intent.getParcelableExtra(key)
    }
}

// Helper to get a Serializable (like UUID) safely
inline fun <reified T : Serializable> Activity.extraSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getSerializableExtra(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        intent.getSerializableExtra(key) as? T
    }
}

// Specific Helper for UUID (uses the generic Serializable helper above)
fun Activity.extraUUID(key: String): UUID? = extraSerializable<UUID>(key)

// Optional: A "Lazy" delegate if you want the value to be ready only when used
inline fun <reified T : Parcelable> Activity.lazyParcelable(key: String): Lazy<T?> = lazy {
    extraParcelable<T>(key)
}