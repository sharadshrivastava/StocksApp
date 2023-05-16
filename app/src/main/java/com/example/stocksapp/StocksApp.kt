package com.example.stocksapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant

@HiltAndroidApp
class StocksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        plant(Timber.DebugTree())
    }

}