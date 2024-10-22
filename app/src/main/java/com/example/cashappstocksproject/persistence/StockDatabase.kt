package com.example.cashappstocksproject.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cashappstocksproject.models.StockEntity

@Database(entities = [StockEntity::class], version = 2)
abstract class StockDatabase : RoomDatabase() {
    abstract fun stockDao(): StocksDao
}
