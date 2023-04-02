package com.example.stocksapp.view.stocklist

import com.example.stocksapp.domain.model.ui.StockItem

sealed interface StocksViewState {
    object Loading : StocksViewState
    data class Success(val stocks: List<StockItem>) : StocksViewState
    data class Error(val errorMessage: String?) : StocksViewState
    object Empty : StocksViewState
}
