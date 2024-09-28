package com.example.cashappstocksproject.ui.views.stocks

import androidx.lifecycle.viewModelScope
import com.example.cashappstocksproject.models.ApiOption
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.ui.views.BaseViewModel
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val stocksService: StocksService
) : BaseViewModel<Event, State, Effect>() {
    override fun setInitialState() = State(
        stocks = emptyList(),
        isLoading = false,
        showError = false
    )
    override fun handleEvents(event: Event) {
        when (event) {
            is Event.CallApiByOption -> callApiByOption(event.option)
            Event.OnBack -> { setEffect(Effect.ToPreviousScreen) }
        }
    }

    private fun callApiByOption(option: String) {
        setState { copy(isLoading = true) }
        viewModelScope.launch {
            val result = when (option) {
                ApiOption.NORMAL.value -> stocksService.getStocks()
                ApiOption.EMPTY.value -> stocksService.getEmptyStocks()
                ApiOption.ERROR.value -> stocksService.getErrorStocks()
                else -> stocksService.getStocks()
            }
            if (result != null) {
                setState { copy(stocks = result.stocks) }
            } else {
                setState { copy(showError = true) }
            }
            setState { copy(isLoading = false) }
        }
    }
}