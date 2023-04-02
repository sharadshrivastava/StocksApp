package com.example.stocksapp.domain

import com.example.stocksapp.domain.model.dto.StocksResponseResult

interface StocksRepository {

    suspend fun stocks(): StocksResponseResult

}