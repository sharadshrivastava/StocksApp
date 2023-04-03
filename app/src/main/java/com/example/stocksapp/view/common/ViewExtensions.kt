package com.example.stocksapp.view.common

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration

fun RecyclerView.addItemDecorationWithoutLastDivider() {
    if (layoutManager !is LinearLayoutManager)
        return

    val itemDecoration = MaterialDividerItemDecoration(
        context,
        (layoutManager as LinearLayoutManager).orientation
    )
    itemDecoration.isLastItemDecorated = false
    addItemDecoration(itemDecoration)
}