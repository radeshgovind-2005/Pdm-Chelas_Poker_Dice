package isel.pdm.pokerdice.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import isel.pdm.pokerdice.BaseVmLog
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<StateType> : ViewModel() {

    //Private mutable viewmodel state
    private val _state = MutableStateFlow(initialState)

    //Public read-only viewmodel state
    val state: StateFlow<StateType> = _state.asStateFlow()

    //Initial default state
    protected abstract val initialState: StateType
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
                BaseVmLog.logException(e)
                onError(e)?.let { newState ->
                    _state.value = newState
                }
            }
        }
    }

    protected fun updateState(newState: StateType) {
        _state.value = newState
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}