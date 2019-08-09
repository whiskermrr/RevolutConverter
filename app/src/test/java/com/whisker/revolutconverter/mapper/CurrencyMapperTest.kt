package com.whisker.revolutconverter.mapper

import com.whisker.revolutconverter.api.response.RevolutConverterResponse
import com.whisker.revolutconverter.model.Currency
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test

class CurrencyMapperTest {

    private val rateMap = mapOf(
        "EUR" to 0.23f,
        "PLN" to 2.3f,
        "RUB" to 11.4f,
        "ISK" to 34.12f
    )

    private val response = RevolutConverterResponse("CZK", "2018-09-06", rateMap)

    private val currencyList = listOf(
        Currency("EUR", 0.23f),
        Currency("PLN", 2.3f),
        Currency("RUB", 11.4f),
        Currency("ISK", 34.12f)
    )

    private val responseCurrencyList = listOf(
        Currency("CZK", 1f),
        Currency("EUR", 0.23f),
        Currency("PLN", 2.3f),
        Currency("RUB", 11.4f),
        Currency("ISK", 34.12f)
    )

    @Before
    fun setUp() {
    }

    @Test
    fun transformMapTest() {
        val convertedCurrencyList = CurrencyMapper.transformFromMap(rateMap)
        assertEquals(convertedCurrencyList, currencyList)
    }

    @Test
    fun transformResponseTest() {
        val convertedCurrencyList = CurrencyMapper.transformFromResponse(response)
        assertEquals(convertedCurrencyList, responseCurrencyList)
    }

    @After
    fun tearDown() {
    }
}