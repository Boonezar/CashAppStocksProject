package com.example.cashappstocksproject.ui.views.stocks

import com.example.cashappstocksproject.models.StockDTO
import com.example.cashappstocksproject.ui.views.ViewEffect
import com.example.cashappstocksproject.ui.views.ViewEvent
import com.example.cashappstocksproject.ui.views.ViewState

class StocksContract {
    sealed class Event: ViewEvent {
        data class CallApiByOption(val option: String): Event()
        data object OnBack: Event()
    }
    data class State(
        val stocks: List<StockDTO>,
        val isLoading: Boolean,
        val showError: Boolean
    ): ViewState
    sealed class Effect: ViewEffect {
        data object ToPreviousScreen: Effect()
    }
}