package com.whisker.revolutconverter.api.response

import com.google.gson.annotations.SerializedName

data class RevolutConverterResponse(
    @SerializedName("base")
    val base: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rates")
    val rates: Map<String, Float>
)