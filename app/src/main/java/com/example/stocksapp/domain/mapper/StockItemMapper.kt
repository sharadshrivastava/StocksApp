package com.example.stocksapp.domain.mapper

import com.example.stocksapp.data.common.parseToString
import com.example.stocksapp.domain.model.dto.StocksResponse
import com.example.stocksapp.domain.model.ui.StockItem
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.inject.Inject

class StockItemMapper @Inject constructor() {

    fun mapToStockItems(stocks: List<StocksResponse.StockItem?>) =
        stocks.map {
            toStockItem(it)
        }

    /**
    As per business requirement, mandatory fields shouldn't be null
    so used 'requireNotNull' for all mandatory non nullable fields.
    If these fields are nullable then in viewModel coroutine exception will be caught, which will show error.
    Only 'quantity' field is nullable.
     */
    private fun toStockItem(stockItemResponse: StocksResponse.StockItem?) = StockItem(
        currentPrice = toDollars(requireNotNull(stockItemResponse?.currentPriceCents)),
        ticker = requireNotNull(stockItemResponse?.ticker),
        name = requireNotNull(stockItemResponse?.name),
        currency = requireNotNull(stockItemResponse?.currency),
        currentPriceTime = formatTime(requireNotNull(stockItemResponse?.currentPriceTimestamp)),
        quantity = stockItemResponse?.quantity
    )

    private fun formatTime(timestamp: Int) =
        Date.from(Instant.ofEpochSecond(timestamp.toLong())).parseToString()

    private fun toDollars(cents: Int) =
        BigDecimal(cents).divide(BigDecimal.valueOf(100)).toString()
}

