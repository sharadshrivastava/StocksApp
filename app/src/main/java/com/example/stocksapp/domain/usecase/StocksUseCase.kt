package com.example.stocksapp.domain.usecase

import com.example.stocksapp.domain.StocksRepository
import com.example.stocksapp.domain.mapper.StockItemMapper
import com.example.stocksapp.domain.model.dto.StocksResponseResult
import com.example.stocksapp.domain.model.ui.StocksResult
import javax.inject.Inject

class StocksUseCase @Inject constructor(
    private val repository: StocksRepository,
    private val mapper: StockItemMapper
) {
    suspend fun stocks(): StocksResult = when (val stocksResponseResult = repository.stocks()) {
        is StocksResponseResult.Success -> handleSuccessResponse(stocksResponseResult)

        is StocksResponseResult.Error -> StocksResult.Error(stocksResponseResult.errorMessage)
    }

    //passing emptyList() when stocks list is null because
    // response is success with no data, to set empty view state.
    private fun handleSuccessResponse(stocksResponseResult: StocksResponseResult.Success) =
        stocksResponseResult.stocksResponse?.stocks?.let {
            StocksResult.Success(mapper.mapToStockItems(it))
        } ?: StocksResult.Success(emptyList())
}