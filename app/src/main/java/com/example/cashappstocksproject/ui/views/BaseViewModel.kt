package com.example.cashappstocksproject.ui.views

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewEffect

const val VIEW_EFFECTS_KEY = "view_effects_key"

abstract class BaseViewModel<Event: ViewEvent, UiState: ViewState, Effect: ViewEffect>: ViewModel() {
    abstract fun setInitialState(): UiState
    abstract fun handleEvents(event: Event)

    private val initialState: UiState by lazy { setInitialState() }
    private val _viewState: MutableState<UiState> by lazy { mutableStateOf(initialState) }
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val _effect: Channel<Effect> = Channel()
    val viewState: State<UiState> by lazy { _viewState }
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect { handleEvents(it) }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}