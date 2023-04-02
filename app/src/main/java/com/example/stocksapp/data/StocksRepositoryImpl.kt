package com.example.stocksapp.data

import com.example.stocksapp.domain.model.dto.StocksResponseResult
import com.example.stocksapp.data.network.StocksApi
import com.example.stocksapp.domain.StocksRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This is a concrete implementation of repository.
 */
@Singleton
class StocksRepositoryImpl @Inject constructor(
    private val api: StocksApi
) : StocksRepository {

    override suspend fun stocks(): StocksResponseResult = try {
        val stocks = api.stocks()
        StocksResponseResult.Success(stocks)

    } catch (e: Exception) {
        StocksResponseResult.Error(e.message)
    }
}