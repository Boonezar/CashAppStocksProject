package com.example.cashappstocksproject.ui.views.dashboard

import com.example.cashappstocksproject.models.ApiOption.*
import com.example.cashappstocksproject.ui.views.BaseViewModel
import com.example.cashappstocksproject.ui.views.dashboard.DashboardContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(): BaseViewModel<Event, State, Effect>() {
    override fun setInitialState() = State
    override fun handleEvents(event: Event) {
        when (event) {
            Event.OnGetStocks -> setEffect(Effect.ToStocksScreen(NORMAL))
            Event.OnGetEmptyStocks -> setEffect(Effect.ToStocksScreen(EMPTY))
            Event.OnGetErrorStocks -> setEffect(Effect.ToStocksScreen(ERROR))
        }
    }
}