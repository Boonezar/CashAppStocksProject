package com.example.cashappstocksproject.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StockEntity (
    @PrimaryKey val ticker: String,
    @ColumnInfo val name: String,
    @ColumnInfo val currency: String,
    @ColumnInfo val currentPriceCents: Int,
    @ColumnInfo val quantity: Int?,
    @ColumnInfo val currentPriceTimestamp: Int
)