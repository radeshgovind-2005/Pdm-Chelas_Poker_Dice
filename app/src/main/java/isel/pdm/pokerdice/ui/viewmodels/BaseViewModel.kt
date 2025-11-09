package isel.pdm.pokerdice.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<StateType> : ViewModel() {
    var state: StateType by mutableStateOf(initialState)
        protected set

    protected abstract val initialState: StateType

    private val tag = this::class.java.simpleName
    private var job: Job? = null

    protected fun launch(
        onError: (Throwable) -> StateType? = { null },
        code: suspend () -> Unit
    ) {
        job?.cancel()
        job = viewModelScope.launch {
            try {
                code()
            } catch (e: Exception) {
                Log.e(tag, e.toString())
                onError(e)?.let { newState ->
                    state = newState
                }
            }
        }
    }

    protected fun updateState(newState: StateType) {
        state = newState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}