package common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel<State, Event>: ViewModel() {

    protected val _uiState = MutableStateFlow(startState())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    protected val _uiEvents = MutableSharedFlow<Event>()
    val uiEvents = _uiEvents.asSharedFlow()

    abstract fun startState(): State

    protected fun launchUI(onError: suspend (Exception) -> Unit = {}, block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (ex: Exception) {
                onError(ex)
            }
        }
    }

    protected fun launchIO(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { block() }
    }

    suspend fun <T> withUI(block: suspend () -> T): T {
        return withContext(Dispatchers.Main) { block() }
    }

    suspend fun <T> withIO(block: suspend () -> T): T {
        return withContext(Dispatchers.IO) { block() }
    }
}