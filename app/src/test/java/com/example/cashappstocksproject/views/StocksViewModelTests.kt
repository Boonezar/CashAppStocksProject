package com.example.cashappstocksproject.views

import com.example.cashappstocksproject.MainDispatcherRule
import com.example.cashappstocksproject.TestData.emptyStocksDTO
import com.example.cashappstocksproject.TestData.mockStocksDto
import com.example.cashappstocksproject.models.StockDTO
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.Event.*
import com.example.cashappstocksproject.ui.views.stocks.StocksContract.State
import com.example.cashappstocksproject.ui.views.stocks.StocksViewModel
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StocksViewModelTests {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var stocksService: StocksService

    private lateinit var sut: StocksViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = StocksViewModel(stocksService)
    }

    @Test
    fun stocksViewModel_setInitialState_stateSetCorrectly() {
        // Given
        // When
        val result = sut.setInitialState()
        // Then
        Assert.assertEquals(State(emptyList(), emptyList(), false, showError = false, searchValue = "", isInit = false), result)
    }

    @Test
    fun stocksViewModel_handleEventsCallApiByOption_NormalApiOptionSetsStocksCorrectly() = runTest {
        // Given
        whenever(stocksService.getStocks()).thenReturn(mockStocksDto)
        // When
        sut.handleEvents(CallApiByOption("normal"))
        advanceUntilIdle()
        // Then
        Assert.assertEquals(mockStocksDto.stocks, sut.viewState.value.stocks)
        verify(stocksService).getStocks()
        Assert.assertEquals(true, sut.viewState.value.isInit)
    }

    @Test
    fun stocksViewModel_handleEventsCallApiByOption_EmptyApiOptionSetsStocksCorrectly() = runTest {
        // Given
        whenever(stocksService.getEmptyStocks()).thenReturn(emptyStocksDTO)
        // When
        sut.handleEvents(CallApiByOption("empty"))
        advanceUntilIdle()
        // Then
        Assert.assertEquals(emptyList<StockDTO>(), sut.viewState.value.stocks)
        verify(stocksService).getEmptyStocks()
        Assert.assertEquals(true, sut.viewState.value.isInit)
    }

    @Test
    fun stocksViewModel_handleEventsCallApiByOption_ErrorApiOptionSetsStocksCorrectly() = runTest {
        // Given
        whenever(stocksService.getErrorStocks()).thenReturn(null)
        // When
        sut.handleEvents(CallApiByOption("error"))
        advanceUntilIdle()
        // Then
        Assert.assertEquals(emptyList<StockDTO>(), sut.viewState.value.stocks)
        Assert.assertEquals(true, sut.viewState.value.showError)
        verify(stocksService).getErrorStocks()
        Assert.assertEquals(true, sut.viewState.value.isInit)
    }

    @Test
    fun stocksViewModel_handleEventsOnBack_SetsEffectCorrectly() = runTest {
        // Given
        // When
        sut.handleEvents(OnBack)
        advanceUntilIdle()
        // Then
        Assert.assertEquals(emptyList<StockDTO>(), sut.viewState.value.stocks)
    }

    @Test
    fun stocksViewModel_handleEventsOnSearchStringChange_searchFieldSetCorrectly() = runTest {
        // Given
        sut.setState { copy(stocks = mockStocksDto.stocks, filteredStocks = mockStocksDto.stocks) }
        Assert.assertEquals(3, sut.viewState.value.filteredStocks.size)
        // When
        sut.handleEvents(OnSearchStringChange("name"))
        // Then
        Assert.assertEquals(2, sut.viewState.value.filteredStocks.size)
        Assert.assertEquals("name", sut.viewState.value.searchValue)
    }

    @Test
    fun stocksViewModel_handleEventsOnSearchStringChange_textFiltersByNameCorrectly() = runTest {
        // Given
        sut.setState { copy(stocks = mockStocksDto.stocks, filteredStocks = mockStocksDto.stocks) }
        Assert.assertEquals(3, sut.viewState.value.filteredStocks.size)
        // When
        sut.handleEvents(OnSearchStringChange("name"))
        // Then
        Assert.assertEquals(2, sut.viewState.value.filteredStocks.size)
        Assert.assertEquals("name", sut.viewState.value.searchValue)
        Assert.assertEquals(listOf(mockStocksDto.stocks[0], mockStocksDto.stocks[1]), sut.viewState.value.filteredStocks)
    }
    // other tests
    // byTicker
    // byName check lowercase
    // byTicker check lowercase
    // empty string -> show all
    // bad string -> empty list
}