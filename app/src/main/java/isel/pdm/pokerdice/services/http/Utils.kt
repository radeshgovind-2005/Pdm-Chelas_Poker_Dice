package isel.pdm.pokerdice.services.http

import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

const val NGROK_ID = "https://semiclinical-starchlike-vania.ngrok-free.dev"
const val BASE_URL = NGROK_ID + "/chelas-poker-dice/"
val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
suspend fun okhttp3.Call.await(): okhttp3.Response = suspendCancellableCoroutine { continuation ->
    enqueue(object : okhttp3.Callback {
        override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
            continuation.resume(response)
        }

        override fun onFailure(call: okhttp3.Call, e: IOException) {
            if (continuation.isActive) {
                continuation.resumeWithException(e)
            }
        }
    })
}

