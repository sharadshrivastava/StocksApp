package com.example.stocksapp.view

import com.example.stocksapp.domain.model.ui.StockItem

sealed class StocksViewState {

    object Loading : StocksViewState()

    data class Success(val stocks: List<StockItem>) : StocksViewState()

    data class Error(val errorMessage: String?) : StocksViewState()

    object Empty : StocksViewState()
}
