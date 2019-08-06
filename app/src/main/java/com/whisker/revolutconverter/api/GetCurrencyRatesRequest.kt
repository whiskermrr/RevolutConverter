package com.whisker.revolutconverter.api

data class GetCurrencyRatesRequest(
    var currencyCode: String
) {

    fun queryMap() : Map<String, String> {
        val map = HashMap<String, String>()
        map["base"] = currencyCode
        return map
    }
}