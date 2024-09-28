package com.example.cashappstocksproject.di

import com.example.cashappstocksproject.network.stocks.StocksApiService
import com.example.cashappstocksproject.network.stocks.StocksService
import com.example.cashappstocksproject.network.stocks.StocksServiceImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}