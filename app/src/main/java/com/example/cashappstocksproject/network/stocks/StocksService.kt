package com.example.cashappstocksproject.network.stocks

import com.example.cashappstocksproject.models.StocksDTO

interface StocksService {
    suspend fun getStocks(): StocksDTO?
    suspend fun getEmptyStocks(): StocksDTO?
    suspend fun getErrorStocks(): StocksDTO?
}