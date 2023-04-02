package com.example.stocksapp.domain.usecases

import com.example.stocksapp.data.StocksRepositoryImpl
import com.example.stocksapp.domain.model.dto.StocksResponseResult
import com.example.stocksapp.domain.model.ui.StocksResult
import javax.inject.Inject

class StocksUseCase @Inject constructor(
    private val repository: StocksRepositoryImpl, private val mapper: StockItemMapper
) {
    suspend fun stocks(): StocksResult = when (val stocksResponseResult = repository.stocks()) {
        is StocksResponseResult.Success -> {
            stocksResponseResult.stocksResponse?.stocks?.let {
                StocksResult.Success(mapper.mapToStockItems(it))
            }   //passing emptyList() because response is success with no data, to set empty view state.
                ?: StocksResult.Success(emptyList())
        }

        is StocksResponseResult.Error -> StocksResult.Error(stocksResponseResult.errorMessage)
    }
}