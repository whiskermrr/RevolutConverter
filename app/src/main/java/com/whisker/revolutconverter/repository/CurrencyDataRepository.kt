package com.whisker.revolutconverter.repository

import com.whisker.revolutconverter.api.request.GetCurrencyRatesRequest
import com.whisker.revolutconverter.api.service.RevolutConverterService
import com.whisker.revolutconverter.mapper.CurrencyMapper
import com.whisker.revolutconverter.model.Currency
import io.reactivex.Flowable
import java.util.concurrent.TimeUnit

class CurrencyDataRepository(
        private val revolutConverterService: RevolutConverterService
) : CurrencyRepository {

    override fun getCurrencyConversion(currencyCode: String): Flowable<List<Currency>> {
        return revolutConverterService
                .getCurrencyRates(GetCurrencyRatesRequest(currencyCode).queryMap())
                .map { CurrencyMapper.transformFromResponse(it) }
                .repeatWhen { Flowable.interval(1000, TimeUnit.MILLISECONDS) }
    }
}