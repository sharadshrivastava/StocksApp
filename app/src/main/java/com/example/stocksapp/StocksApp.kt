package com.example.stocksapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StocksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        var appContext: StocksApp? =
            null //It can be null in testing environment, so initialised it as nullable.
    }
}