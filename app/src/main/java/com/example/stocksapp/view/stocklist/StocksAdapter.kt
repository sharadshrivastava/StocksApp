package com.example.stocksapp.view.stocklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.stocksapp.databinding.ItemStockBinding
import com.example.stocksapp.domain.model.ui.StockItem

class StocksAdapter : ListAdapter<StockItem, StockItemViewHolder>(NumberDiffCallback()) {

    /* Creates and inflates view and return StockItemViewHolder. */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        StockItemViewHolder(
            ItemStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    /* Gets current stockItem and uses it to bind view. */
    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) =
        holder.bind(getItem(position))


    //if the new list is submitted to this adapter
    //then DiffUtil will take care of updating only different items.
    class NumberDiffCallback : DiffUtil.ItemCallback<StockItem>() {

        override fun areItemsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.ticker == newItem.ticker
        }

        override fun areContentsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem.currentPrice == newItem.currentPrice
                    && oldItem.currentPriceTime == newItem.currentPriceTime
                    && oldItem.quantity == newItem.quantity
        }
    }
}

