package com.example.stocksapp.domain.model.dto

sealed class StocksResponseResult {

    data class Success(val stocksResponse: StocksResponse?) : StocksResponseResult()

    data class Error(val errorMessage: String?) : StocksResponseResult()
}
