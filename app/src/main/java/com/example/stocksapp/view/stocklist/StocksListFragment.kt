package com.example.stocksapp.view.stocklist

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
        StocksViewState.Loading -> handleLoadingState()
        StocksViewState.Empty -> handleEmptyState()
        is StocksViewState.Success -> handleSuccessState(viewState.stocks)
        is StocksViewState.Error -> handleErrorState(viewState.errorMessage)
    }

    private fun handleLoadingState() {
        binding.stocksList.visibility = View.GONE
        binding.errorContainer.root.visibility = View.GONE
        binding.emptyContainer.root.visibility = View.GONE

        binding.loading.visibility = View.VISIBLE
    }

    private fun handleSuccessState(stocks: List<StockItem>) {
        binding.loading.visibility = View.GONE
        binding.errorContainer.root.visibility = View.GONE
        binding.emptyContainer.root.visibility = View.GONE

        binding.stocksList.visibility = View.VISIBLE
        (binding.stocksList.adapter as StocksAdapter).submitList(stocks)
    }

    private fun handleErrorState(errorMessage: String?) {
        binding.stocksList.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.emptyContainer.root.visibility = View.GONE

        binding.errorContainer.root.visibility = View.VISIBLE
        binding.errorContainer.errorText.text = errorMessage
    }

    private fun handleEmptyState() {
        binding.stocksList.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.errorContainer.root.visibility = View.GONE

        binding.emptyContainer.root.visibility = View.VISIBLE
    }
}