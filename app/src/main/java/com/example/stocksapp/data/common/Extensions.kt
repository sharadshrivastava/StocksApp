package com.example.stocksapp.data.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.parseToString(format: String = "d MMM yyyy, HH:mm:ss"): String =
    SimpleDateFormat(format, Locale.ENGLISH).format(this)
