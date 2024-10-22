package com.example.cashappstocksproject.repository

import com.example.cashappstocksproject.models.StocksDTO

interface StocksRepository {
    suspend fun getStocks(): StocksDTO?
}