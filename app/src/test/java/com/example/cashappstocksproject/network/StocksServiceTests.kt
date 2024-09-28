package com.example.cashappstocksproject.network

import com.example.cashappstocksproject.TestData.emptyStocksDTO
import com.example.cashappstocksproject.TestData.mockStocksDto
import com.example.cashappstocksproject.network.stocks.StocksApiService
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.network.stocks.StocksServiceImpl
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StocksServiceTests {
    @Mock
    private lateinit var stocksApiService: StocksApiService

    private lateinit var sut: StocksService

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = StocksServiceImpl(stocksApiService)
    }

    @Test
    fun stocksService_getStocks_returnsFromApiService() = runTest {
        // Given
        whenever(stocksApiService.getStocks()).thenReturn(mockStocksDto)
        // When
        val result = sut.getStocks()
        // Then
        Assert.assertEquals(mockStocksDto, result)
    }

    @Test
    fun stocksService_getEmptyStocks_returnsFromApiService() = runTest {
        // Given
        whenever(stocksApiService.getEmptyStocks()).thenReturn(emptyStocksDTO)
        // When
        val result = sut.getEmptyStocks()
        // Then
        Assert.assertEquals(emptyStocksDTO, result)
    }

    @Test
    fun stocksService_getErrorStocks_exceptionReturnsNull() = runTest {
        // Given
        val error = Exception("mockError")
        whenever(stocksApiService.getErrorStocks()).then { throw error }
        // When
        val result = sut.getErrorStocks()
        // Then
        Assert.assertEquals(null, result)
    }
}