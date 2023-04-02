package com.example.stocksapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksapp.databinding.FragmentStocksListBinding
import com.example.stocksapp.domain.model.ui.StockItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StocksListFragment : Fragment() {

    private lateinit var binding: FragmentStocksListBinding

    private val stocksViewModel: StocksViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentStocksListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupList()
        observeViewState()
    }

    private fun observeViewState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                stocksViewModel.viewState.collect {
                    handleViewState(it)
                }
            }
        }
    }

    private fun setupList() {
        with(binding.stocksList) {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
            adapter = StocksAdapter()
        }
    }

    private fun handleViewState(viewState: StocksViewState) = when (viewState) {
        StocksViewState.Loading -> {
            binding.loading.visibility = View.VISIBLE
            binding.stocksList.visibility = View.GONE
        }

        is StocksViewState.Success -> {
            handleSuccess(viewState.stocks)
        }

        StocksViewState.Empty -> Unit

        is StocksViewState.Error -> {
            handleError(viewState.errorMessage)
        }
    }

    private fun handleSuccess(stocks: List<StockItem>) {
        binding.loading.visibility = View.GONE
        binding.stocksList.visibility = View.VISIBLE

        (binding.stocksList.adapter as StocksAdapter).submitList(stocks)
    }

    private fun handleError(errorMessage: String?) {
        /*binding.isLoading = false
        binding.isEmpty = true
        showErrorBar(msg = msg)*/
    }
}