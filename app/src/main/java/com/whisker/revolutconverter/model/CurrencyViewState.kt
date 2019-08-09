package com.whisker.revolutconverter.model

sealed class CurrencyViewState {
    class Error(val message: String?) : CurrencyViewState()
    class Data(val currencies: List<Currency>) : CurrencyViewState()
}