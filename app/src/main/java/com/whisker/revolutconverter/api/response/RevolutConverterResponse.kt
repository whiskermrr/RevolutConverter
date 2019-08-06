package com.whisker.revolutconverter.api.response

import com.google.gson.annotations.SerializedName

data class RevolutConverterResponse(
    @SerializedName("base")
    val base: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("rates")
    val rates: HashMap<String, Double>? = null
)