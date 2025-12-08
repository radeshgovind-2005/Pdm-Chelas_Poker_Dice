package isel.pdm.pokerdice.services.http

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources

data class SseEvent(val event: String, val data: String, val id: String?)

fun OkHttpClient.sseFlow(request: Request): Flow<SseEvent> = callbackFlow {
    val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            // Connection opened
        }

        override fun onEvent(eventSource: EventSource, id: String?, type: String?, data: String) {
            trySend(SseEvent(type ?: "message", data, id))
        }

        override fun onClosed(eventSource: EventSource) {
            close() // Close the flow when server closes
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            close(t)// Close the flow on failure (retry tdo)
        }
    }

    val eventSource = EventSources.createFactory(this@sseFlow)
        .newEventSource(request, eventSourceListener)

    awaitClose {
        eventSource.cancel()
    }
}

