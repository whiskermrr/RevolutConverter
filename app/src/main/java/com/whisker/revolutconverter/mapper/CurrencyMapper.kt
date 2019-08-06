package com.whisker.revolutconverter.mapper

import com.whisker.revolutconverter.model.Currency

object CurrencyMapper {

    fun transformFromMap(map: Map<String, Double>) : List<Currency> {
        val currencies: MutableList<Currency> = mutableListOf()
        map.keys.forEach { currencies.add(Currency(it, map[it] ?: 0.0)) }
        return currencies
    }
}