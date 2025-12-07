package isel.pdm.pokerdice.app

import android.util.Log

const val APP_BASE_TAG = "CHELAS_POKER_DICE"

class AppLog(private val category: String){
    private val tag: String = "$APP_BASE_TAG-$category"

    private val isDebug: Boolean = true

    fun lifeCycle(stage:String) {
        if (isDebug) Log.i(tag, "${category}: Life Cycle stage -> $stage")
    }
    fun v(message: String) {
        if (isDebug) Log.v(tag, message)
    }

    fun d(message: String) {
        if (isDebug) Log.d(tag, "${category}: $message")
    }

    fun i(message: String) {
        if (isDebug) Log.i(tag, "$category: $message")
    }

    fun w(message: String, throwable: Throwable? = null) {
        if (isDebug) {
            if (throwable != null) Log.w(tag, message, throwable)
            else Log.w(tag, message)
        }
    }

    fun e(message: String, throwable: Throwable? = null) {
        if (throwable != null) Log.e(tag, message, throwable)
        else Log.e(tag, message)
    }
}