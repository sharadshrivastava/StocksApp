package com.example.stocksapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.stocksapp.data.StocksRepositoryImpl
import com.example.stocksapp.domain.mapper.StockItemMapper
import com.example.stocksapp.domain.model.dto.StocksResponse
import com.example.stocksapp.domain.model.dto.StocksResponseResult
import com.example.stocksapp.domain.model.ui.StockItem
import com.example.stocksapp.domain.model.ui.StocksResult
import com.example.stocksapp.domain.usecase.StocksUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StocksUseCaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val dispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var repository: StocksRepositoryImpl

    private lateinit var useCase: StocksUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testStocks_successScenario() = runTest {
        //when
        whenever(repository.stocks()).thenReturn(
            StocksResponseResult.Success(
                StocksResponse(stocks())
            )
        )

        //then
        performCommonAction()

        //verify
        val stocksResult = useCase.stocks()

        assertIs<StocksResult.Success>(stocksResult)
        assertTrue { stocksResult.stocks.size == 2 }

        val (firstStock, secondStock) = stocksResult.stocks
        testStock(
            expectedTicker = "TWTR",
            expectedName = "Twitter Inc.",
            expectedCurrentPrice = "38.33", //price and time are the formatted values from mapper.
            expectedTime = "12 Nov 2021, 06:08:08",
            actualStockItem = firstStock
        )

        testStock(
            expectedTicker = "APPLE",
            expectedName = "Apple Inc.",
            expectedCurrentPrice = "20.13",
            expectedTime = "12 Nov 2021, 06:08:10",
            actualStockItem = secondStock
        )
        //In this way we can test other fields as well by passing to testStock function.
    }

    private fun testStock(
        expectedTicker: String,
        expectedName: String,
        expectedCurrentPrice: String,
        expectedTime: String,
        actualStockItem: StockItem
    ) {
        assertEquals(expectedTicker, actualStockItem.ticker)
        assertEquals(expectedName, actualStockItem.name)
        assertEquals(expectedCurrentPrice, actualStockItem.currentPrice)
        assertEquals(expectedTime, actualStockItem.currentPriceTime)
    }

    @Test
    fun testStocks_errorScenario() = runTest {
        //when
        whenever(repository.stocks()).thenReturn(
            StocksResponseResult.Error("internal server error")
        )

        //then
        performCommonAction()

        //verify
        val stocksResult = useCase.stocks()

        assertIs<StocksResult.Error>(stocksResult)
        assertEquals("internal server error", stocksResult.errorMessage)
    }

    /**
     * Testing that mandatory fields should not be null as per requirement.
     * only 'quantity' field is nullable.
     */
    @Test
    fun testStocks_negativeScenarios() = runTest {
        //when ticker is null
        whenever(repository.stocks()).thenReturn(
            negativeScenarioResponse(stockItem().copy(ticker = null))
        )

        //then
        performCommonAction()

        //verify
        assertFailsWith<IllegalArgumentException> {
            useCase.stocks()
        }

        //when name is null
        whenever(repository.stocks()).thenReturn(
            negativeScenarioResponse(stockItem().copy(name = null))
        )

        //then
        performCommonAction()

        //verify
        assertFailsWith<IllegalArgumentException> {
            useCase.stocks()
        }

        //In this way we can test if mandatory items are null it should fail.
    }

    private fun negativeScenarioResponse(invalidStockItem: StocksResponse.StockItem) =
        StocksResponseResult.Success(
            StocksResponse(
                listOf(invalidStockItem)
            )
        )

    private fun performCommonAction() {
        useCase = StocksUseCase(repository, StockItemMapper())
    }


    /**
     * Data Setup
     */
    private fun stocks() = listOf(
        stockItem(), StocksResponse.StockItem(
            currentPriceCents = 2013,
            ticker = "APPLE",
            quantity = 10,
            name = "Apple Inc.",
            currency = "USD",
            currentPriceTimestamp = 1636657690
        )
    )

    private fun stockItem() = StocksResponse.StockItem(
        currentPriceCents = 3833,
        ticker = "TWTR",
        quantity = 25,
        name = "Twitter Inc.",
        currency = "USD",
        currentPriceTimestamp = 1636657688
    )
}