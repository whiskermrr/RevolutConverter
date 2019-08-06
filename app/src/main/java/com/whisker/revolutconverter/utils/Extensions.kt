package com.whisker.revolutconverter.utils

import android.content.Context
import android.net.ConnectivityManager

fun Context.isNetworkAvailable() : Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.isConnected
}