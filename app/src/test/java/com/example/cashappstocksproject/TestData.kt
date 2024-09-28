package com.example.cashappstocksproject

import com.example.cashappstocksproject.models.StockDTO
import com.example.cashappstocksproject.models.StocksDTO

object TestData {
    val mockStocksDto = StocksDTO(
        stocks = listOf(
            StockDTO(
                ticker = "mockTicker",
                name = "mockName",
                currency = "mockCurrency",
                currentPriceCents = 1,
                quantity = null,
                currentPriceTimestamp = 1681845832
            )
        )
    )
    val emptyStocksDTO = StocksDTO(stocks = emptyList())
}