package com.example.stocksapp.domain.mapper

import com.example.stocksapp.data.common.parseToString
import com.example.stocksapp.domain.model.dto.StocksResponse
import com.example.stocksapp.domain.model.ui.StockItem
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.inject.Inject

class StockItemMapper @Inject constructor() {

    fun mapToStockItems(stocks: List<StocksResponse.StocksItem?>) =
        //mapping only valid items, all invalid items will be filtered out
        stocks.mapNotNull { stockItemResponse ->
            if (isStockItemResponseValid(stockItemResponse)) {
                toStockItem(stockItemResponse!!)
            } else null
        }

    //In 'isStockItemResponseValid()', it is checked that mandatory fields are not null
    // so it is safe to use "!!" with these fields.
    //Only 'quantity' is nullable as per requirement.
    private fun toStockItem(stocksItemResponse: StocksResponse.StocksItem) = StockItem(
        currentPrice = toDollars(stocksItemResponse.currentPriceCents!!),
        ticker = stocksItemResponse.ticker!!,
        name = stocksItemResponse.name!!,
        currency = stocksItemResponse.currency!!,
        currentPriceTime = formatTime(stocksItemResponse.currentPriceTimestamp!!),
        quantity = stocksItemResponse.quantity
    )

    private fun formatTime(timestamp: Int) =
        Date.from(Instant.ofEpochSecond(timestamp.toLong())).parseToString()

    private fun toDollars(cents: Int) = BigDecimal(cents).divide(BigDecimal.valueOf(100)).toString()

    //As per business requirement, these fields shouldn't be null.
    private fun isStockItemResponseValid(
        stockItem: StocksResponse.StocksItem?
    ) = stockItem?.currentPriceCents != null && stockItem.ticker != null && stockItem.name != null
            && stockItem.currency != null && stockItem.currentPriceTimestamp != null
}

