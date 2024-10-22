package com.example.cashappstocksproject.ui.views.stocks

import com.example.cashappstocksproject.models.StockDTO
import com.example.cashappstocksproject.ui.views.ViewEffect
import com.example.cashappstocksproject.ui.views.ViewEvent
import com.example.cashappstocksproject.ui.views.ViewState

class StocksContract {
    sealed class Event: ViewEvent {
        data class CallApiByOption(val option: String): Event()
        data object OnBack: Event()
        data class OnSearchStringChange(val text: String): Event()
    }
    data class State(
        val stocks: List<StockDTO>,
        val filteredStocks: List<StockDTO>,
        val isLoading: Boolean,
        val showError: Boolean,
        val searchValue: String,
        val isInit: Boolean
    ): ViewState
    sealed class Effect: ViewEffect {
        data object ToPreviousScreen: Effect()
    }
}