package com.example.cashappstocksproject.ui.views.stocks

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.viewModelScope
import com.example.cashappstocksproject.models.ApiOption
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.repository.StocksRepository
import com.example.cashappstocksproject.ui.views.BaseViewModel
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val stocksService: StocksService
    //private val stocksRepository: StocksRepository
) : BaseViewModel<Event, State, Effect>() {
    override fun setInitialState() = State(
        stocks = emptyList(),
        filteredStocks = emptyList(),
        isLoading = false,
        showError = false,
        searchValue = "",
        isInit = false
    )
    override fun handleEvents(event: Event) {
        when (event) {
            is Event.CallApiByOption -> callApiByOption(event.option)
            Event.OnBack -> { setEffect(Effect.ToPreviousScreen) }
            is Event.OnSearchStringChange -> handleOnSearchStringChange(event.text)
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
            //val result = stocksRepository.getStocks()
            if (result != null) {
                setState { copy(stocks = result.stocks, filteredStocks = result.stocks) }
            } else {
                setState { copy(showError = true) }
            }
            setState { copy(isLoading = false, isInit = true) }
        }
    }

    private fun handleOnSearchStringChange(text: String) {
        val filteredList = viewState.value.stocks.filter {
            it.name.lowercase().contains(text.lowercase()) ||
                    it.ticker.lowercase().contains(text.lowercase())
        }
        setState { copy(searchValue = text, filteredStocks = filteredList) }
    }
}