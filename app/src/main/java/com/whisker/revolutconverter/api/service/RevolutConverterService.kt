package com.whisker.revolutconverter.api.service

import com.whisker.revolutconverter.api.response.RevolutConverterResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RevolutConverterService {

    @GET("/latest")
    fun getCurrencyRates(@QueryMap params: Map<String, String>) : Single<RevolutConverterResponse>
}