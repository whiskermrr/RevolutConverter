package com.whisker.revolutconverter.mapper

import com.whisker.revolutconverter.api.response.RevolutConverterResponse
import com.whisker.revolutconverter.model.Currency

object CurrencyMapper {

    fun transformFromResponse(response: RevolutConverterResponse) : List<Currency> {
        val currencies = transformFromMap(response.rates)
        currencies.add(0, Currency(response.base, 1f))
        return currencies
    }

    fun transformFromMap(map: Map<String, Float>) : MutableList<Currency> {
        val currencies: MutableList<Currency> = mutableListOf()
        map.keys.forEach { currencies.add(Currency(it, map[it] ?: 0f)) }
        return currencies
    }
}