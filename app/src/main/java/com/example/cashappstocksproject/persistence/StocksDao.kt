package com.example.cashappstocksproject.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cashappstocksproject.models.StockEntity

@Dao
interface StocksDao {
    @Query("SELECT * FROM StockEntity")
    fun getAll(): List<StockEntity>

    @Insert
    fun insertAll(stocks: List<StockEntity>)
}