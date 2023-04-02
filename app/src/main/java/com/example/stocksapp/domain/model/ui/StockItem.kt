package com.example.stocksapp.domain.model.ui

/**
 * Created separate UI data class for stock item because
 * 1.All fields of DTOs might not required in UI.
 * 2.In future this class can have values from some other DTOs as well.
 * 3.Here we put nullable and non nullable fields as per business requirement.
 */

data class StockItem(

    val currentPrice: String,

    val ticker: String,

    val name: String,

    val currency: String,

    val currentPriceTime: String,

    val quantity: Int?
)
