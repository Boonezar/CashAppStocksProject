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
        Assert.assertEquals(State(emptyList(), false, false), result)
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
}