package com.example.stocksapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stocksapp.domain.model.ui.StockItem
import com.example.stocksapp.domain.model.ui.StocksResult
import com.example.stocksapp.domain.usecase.StocksUseCase
import com.example.stocksapp.view.stocklist.StocksViewModel
import com.example.stocksapp.view.stocklist.StocksViewState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StocksViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var useCase: StocksUseCase

    private lateinit var stocksViewModel: StocksViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testStocks_loadingScenario() = runTest {
        //when
        whenever(useCase.stocks()).thenReturn(null)

        //then
        performCommonAction()

        //verify
        val viewState = stocksViewModel.viewState.value
        assertIs<StocksViewState.Loading>(viewState)
    }

    @Test
    fun testStocks_successScenario() = runTest {
        //when
        whenever(useCase.stocks()).thenReturn(StocksResult.Success(stocks()))

        //then
        performCommonAction()

        //verify
        val viewState = stocksViewModel.viewState.value

        assertIs<StocksViewState.Success>(viewState)
        assertTrue { viewState.stocks.size == 2 }

        val (firstStock, secondStock) = viewState.stocks
        assertEquals("TWTR", firstStock.ticker)
        assertEquals("APPLE", secondStock.ticker)

        //In this way we can test other fields as well.
    }

    @Test
    fun testStocks_emptyScenario() = runTest {
        //when
        whenever(useCase.stocks()).thenReturn(StocksResult.Success(emptyList()))

        //then
        performCommonAction()

        //verify
        val viewState = stocksViewModel.viewState.value
        assertIs<StocksViewState.Empty>(viewState)
    }

    @Test
    fun testStocks_errorScenario() = runTest {
        //when
        whenever(useCase.stocks()).thenReturn(StocksResult.Error("invalid data"))

        //then
        performCommonAction()

        //verify
        val viewState = stocksViewModel.viewState.value

        assertIs<StocksViewState.Error>(viewState)
        assertEquals("invalid data", viewState.errorMessage)
    }

    private fun performCommonAction() {
        stocksViewModel = StocksViewModel(useCase)
    }


    /**
     * Data Setup
     */
    private fun stocks() = listOf(
        StockItem(
            currentPrice = "38.33",
            ticker = "TWTR",
            name = "Twitter, Inc.",
            currency = "USD",
            currentPriceTime = "12 Nov 2021, 06:08:08",
            quantity = 25
        ),
        StockItem(
            currentPrice = "20.13",
            ticker = "APPLE",
            name = "APPLE, Inc.",
            currency = "USD",
            currentPriceTime = "12 Nov 2021, 06:08:08",
            quantity = 10
        )
    )
}