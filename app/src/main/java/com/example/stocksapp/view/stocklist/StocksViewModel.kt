package com.example.stocksapp.view.stocklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocksapp.domain.model.ui.StocksResult
import com.example.stocksapp.domain.usecase.StocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StocksViewModel @Inject constructor(
    private val useCase: StocksUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow<StocksViewState>(StocksViewState.Loading)
    val viewState: StateFlow<StocksViewState> = _viewState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _viewState.value = StocksViewState.Error(throwable.message)
    }

    init {
        requestStocks()
    }

    private fun requestStocks() {
        viewModelScope.launch(exceptionHandler) {
            handleResult(useCase.stocks())
        }
    }

    private fun handleResult(stocksResult: StocksResult) {
        when (stocksResult) {
            is StocksResult.Success -> {
                _viewState.value =
                    if (stocksResult.stocks.isEmpty()) StocksViewState.Empty
                    else StocksViewState.Success(stocksResult.stocks)
            }

            is StocksResult.Error ->
                _viewState.value = StocksViewState.Error(stocksResult.errorMessage)
        }
    }
}