package com.example.stocksapp.view.stocklist

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.stocksapp.R
import com.example.stocksapp.databinding.ItemStockBinding
import com.example.stocksapp.domain.model.ui.StockItem

class StockItemViewHolder(private val binding: ItemStockBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: StockItem) {
        binding.ticker.text = item.ticker
        binding.name.text = item.name
        binding.price.text = item.currentPrice
        binding.currency.text = item.currency
        binding.time.text = item.currentPriceTime

        //quantity is nullable as per requirement.
        if (item.quantity != null) {
            binding.quantity.text =
                binding.root.context.getString(R.string.stock_quantity, item.quantity.toString())
        } else {
            binding.quantity.visibility = View.GONE
        }
    }
}