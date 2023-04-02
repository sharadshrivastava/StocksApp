package com.example.stocksapp.domain.model.dto

import com.google.gson.annotations.SerializedName

data class StocksResponse(

    @field:SerializedName("stocks")
    val stocks: List<StocksItem?>? = null
) {
    data class StocksItem(

        @field:SerializedName("current_price_cents")
        val currentPriceCents: Int? = null,

        @field:SerializedName("ticker")
        val ticker: String? = null,

        @field:SerializedName("quantity")
        val quantity: Int? = null,

        @field:SerializedName("name")
        val name: String? = null,

        @field:SerializedName("currency")
        val currency: String? = null,

        @field:SerializedName("current_price_timestamp")
        val currentPriceTimestamp: Int? = null
    )
}
