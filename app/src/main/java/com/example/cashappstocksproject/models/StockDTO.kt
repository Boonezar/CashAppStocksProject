package com.example.cashappstocksproject.models

import com.squareup.moshi.Json

data class StockDTO(
    val ticker: String,
    val name: String,
    val currency: String,
    @Json(name = "current_price_cents") val currentPriceCents: Int,
    val quantity: Int?,
    @Json(name = "current_price_timestamp") val currentPriceTimestamp: Int
)
