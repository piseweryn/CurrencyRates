package com.seweryn.ratesapplication.data.local.database

import com.seweryn.ratesapplication.data.model.CurrencyRate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class DatabaseResponseParserTest {

    @Test
    fun `base currency should be at the top of the list`() {
        val result = DatabaseResponseParser.parseCurrencyRate(createCurrencyRates(), "PLN")
        assertEquals("PLN", result.first().currencyCode)
        assert(result.first().isBaseCurrency)
        assertEquals(3, result.size)
    }

    @Test
    fun `should correctly calculate rates for selected base currency`() {
        val result = DatabaseResponseParser.parseCurrencyRate(createCurrencyRates(), "PLN")
        assertEquals(0.5f, result.find { it.currencyCode == "EUR" }?.rate)
        assertEquals(1.5f, result.find { it.currencyCode == "USD" }?.rate)
    }

    private fun createCurrencyRates() = listOf(
        CurrencyRate(
            currencyCode = "EUR",
            rate = 1f,
            isBaseCurrency = true
        ), CurrencyRate(
            currencyCode = "PLN",
            rate = 2f,
            isBaseCurrency = false
        ), CurrencyRate(
            currencyCode = "USD",
            rate = 3f,
            isBaseCurrency = false
        )
    )
}