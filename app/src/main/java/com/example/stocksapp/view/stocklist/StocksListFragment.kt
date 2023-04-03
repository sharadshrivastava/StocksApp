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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stocksapp.R
import com.example.stocksapp.databinding.FragmentStocksListBinding
import com.example.stocksapp.domain.model.ui.StockItem
import com.example.stocksapp.view.common.addItemDecorationWithoutLastDivider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

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
            adapter = StocksAdapter()
            addItemDecorationWithoutLastDivider()
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

        //showing user readable error message in UI and logging the technical error message.
        binding.errorContainer.errorText.text = getString(R.string.generic_error_msg)
        Timber.e(errorMessage)
    }

    private fun handleEmptyState() {
        binding.stocksList.visibility = View.GONE
        binding.loading.visibility = View.GONE
        binding.errorContainer.root.visibility = View.GONE

        binding.emptyContainer.root.visibility = View.VISIBLE
    }
}