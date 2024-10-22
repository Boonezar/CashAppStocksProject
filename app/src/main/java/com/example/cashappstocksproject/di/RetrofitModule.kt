package com.example.cashappstocksproject.di

import android.content.Context
import androidx.room.Room
import com.example.cashappstocksproject.network.stocks.StocksApiService
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.network.stocks.StocksServiceImpl
import com.example.cashappstocksproject.persistence.StockDatabase
import com.example.cashappstocksproject.persistence.StocksDao
import com.example.cashappstocksproject.repository.StocksRepository
import com.example.cashappstocksproject.repository.StocksRepositoryImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun providesBaseUrl(): String = "https://storage.googleapis.com/cash-homework/cash-stocks-api/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, moshi: Moshi): Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(baseUrl)
        .build()

    @Provides
    @Singleton
    fun providesStocksApiService(retrofit: Retrofit): StocksApiService =
        retrofit.create(StocksApiService::class.java)

    @Provides
    @Singleton
    fun provideStocksService(apiService: StocksApiService): StocksService = StocksServiceImpl(apiService)

    @Provides
    @Singleton
    fun provideStocksDatabase(@ApplicationContext appContext: Context): StockDatabase = Room.databaseBuilder(
        appContext,
        StockDatabase::class.java,
        "stock-database"
    ).build()

    @Provides
    @Singleton
    fun provideStocksRepository(stocksService: StocksService, stocksDatabase: StockDatabase): StocksRepository = StocksRepositoryImpl(stocksService, stocksDatabase)

}