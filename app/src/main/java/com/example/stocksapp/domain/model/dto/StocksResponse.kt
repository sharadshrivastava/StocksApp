package com.example.stocksapp.domain.model.dto

import com.google.gson.annotations.SerializedName

data class StocksResponse(

    @SerializedName("stocks")
    val stocks: List<StockItem?>?
) {
    data class StockItem(

        @SerializedName("current_price_cents")
        val currentPriceCents: Int?,

        @SerializedName("ticker")
        val ticker: String?,

        @SerializedName("quantity")
        val quantity: Int?,

        @SerializedName("name")
        val name: String?,

        @SerializedName("currency")
        val currency: String?,

        @SerializedName("current_price_timestamp")
        val currentPriceTimestamp: Int?
    )
}
