package com.example.stocksapp.di

import com.example.stocksapp.data.StocksRepositoryImpl
import com.example.stocksapp.data.network.StocksApi
import com.example.stocksapp.data.network.StocksApi.Companion.BASE_URL
import com.example.stocksapp.domain.StocksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt's module to get Retrofit's network interface object.
 * This module is required because 'NumbersApi' doesn't have default constructor.
 */
@InstallIn(SingletonComponent::class)
@Module
object ServiceModule {

    @Provides
    @Singleton
    fun provideApi(): StocksApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StocksApi::class.java)
    }

    /** providing repository so that use cases need not to use
    concrete repository in as param.*/
    @Provides
    @Singleton
    fun provideRepository(api: StocksApi): StocksRepository =
        StocksRepositoryImpl(api)
}