package com.example.cashappstocksproject.ui.views.dashboard

import com.example.cashappstocksproject.models.ApiOption
import com.example.cashappstocksproject.ui.views.ViewEffect
import com.example.cashappstocksproject.ui.views.ViewEvent
import com.example.cashappstocksproject.ui.views.ViewState

class DashboardContract {
    sealed class Event: ViewEvent {
        data object OnGetStocks: Event()
        data object OnGetErrorStocks: Event()
        data object OnGetEmptyStocks: Event()
    }
    object State: ViewState
    sealed class Effect: ViewEffect {
        data class ToStocksScreen(val stockOption: ApiOption): Effect()
    }
}