package com.example.cashappstocksproject.network

abstract class BaseService {
    suspend fun <T: Any> handleRequest(request: suspend () -> T): T? {
        return try {
            request()
        } catch (e: Exception) {
            null
        }
    }
}