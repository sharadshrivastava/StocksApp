package com.example.stocksapp.domain.model.ui

sealed class StocksResult {

    data class Success(val stocks: List<StockItem>) : StocksResult()

    data class Error(val errorMessage: String? = null) : StocksResult()
}
