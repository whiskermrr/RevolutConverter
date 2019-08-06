package com.whisker.revolutconverter.repository

import com.whisker.revolutconverter.model.Currency
import io.reactivex.Flowable

interface CurrencyRepository {
    fun getCurrencyConversion(currencyCode: String) : Flowable<List<Currency>>
}