package com.example.cashappstocksproject.repository

import com.example.cashappstocksproject.models.StockDTO
import com.example.cashappstocksproject.models.StockEntity
import com.example.cashappstocksproject.models.StocksDTO
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.persistence.StockDatabase
import com.example.cashappstocksproject.persistence.StocksDao
import javax.inject.Inject

class StocksRepositoryImpl @Inject constructor(
    private val stocksService: StocksService,
    private val stocksDatabase: StockDatabase
) : StocksRepository {
    override suspend fun getStocks(): StocksDTO? {
        val stocksFromDb = stocksDatabase.stockDao().getAll()
        return if (stocksFromDb.isEmpty()) {
            println("SAM: from db")
            val stocksFromService = stocksService.getStocks()
            val list = mutableListOf<StockEntity>()
            stocksFromService?.stocks?.forEach { stock ->
                list.add(StockEntity(
                    ticker = stock.ticker,
                    name = stock.name,
                    currency = stock.currency,
                    currentPriceTimestamp = stock.currentPriceTimestamp,
                    currentPriceCents = stock.currentPriceCents,
                    quantity = stock.quantity
                ))
            }
            stocksDatabase.stockDao().insertAll(list)
            stocksFromService
        } else {
            println("SAM: from service")
            val list = mutableListOf<StockDTO>()
            stocksFromDb.forEach { stock ->
                list.add(
                    StockDTO(
                        ticker = stock.ticker,
                        name = stock.name,
                        currency = stock.currency,
                        currentPriceTimestamp = stock.currentPriceTimestamp,
                        currentPriceCents = stock.currentPriceCents,
                        quantity = stock.quantity
                ))
            }
            StocksDTO(stocks = list)
        }
    }
}