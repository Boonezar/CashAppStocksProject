package com.example.cashappstocksproject.network.stocks

import com.example.cashappstocksproject.models.StocksDTO
import retrofit2.http.GET

interface StocksApiService {
    @GET("portfolio.json")
    suspend fun getStocks(): StocksDTO
    @GET("portfolio_empty.json")
    suspend fun getEmptyStocks(): StocksDTO
    @GET("portfolio_malformed.json")
    suspend fun getErrorStocks(): StocksDTO
}