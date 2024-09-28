package com.example.cashappstocksproject.network.stocks

import com.example.cashappstocksproject.network.BaseService
import javax.inject.Inject

class StocksServiceImpl @Inject constructor(
    private val stocksApiService: StocksApiService
): StocksService, BaseService() {
    override suspend fun getStocks() = handleRequest { stocksApiService.getStocks() }
    override suspend fun getEmptyStocks() = handleRequest { stocksApiService.getEmptyStocks() }
    override suspend fun getErrorStocks() = handleRequest { stocksApiService.getErrorStocks() }
}