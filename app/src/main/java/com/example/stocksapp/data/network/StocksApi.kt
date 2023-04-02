package com.example.stocksapp.data.network

import com.example.stocksapp.domain.model.dto.StocksResponse
import retrofit2.http.GET

/**
 * Retrofit library's network interface.
 */
interface StocksApi {

    @GET(STOCKS_PATH)
    suspend fun stocks(): StocksResponse?

    companion object {
        const val BASE_URL = "https://storage.googleapis.com/"
        private const val STOCKS_PATH = "cash-homework/cash-stocks-api/portfolio.json"
    }
}