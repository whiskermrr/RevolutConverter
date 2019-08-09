package com.whisker.revolutconverter.utils

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.whisker.revolutconverter.R

fun Context.isNetworkAvailable() : Boolean {
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo.isConnected
}

fun ImageView.loadFlag(currencyCode: String) {
    val flagDrawable = try {
        val resourceID = context.resources.getIdentifier(currencyCode.toLowerCase(), "drawable", context.packageName)
        ContextCompat.getDrawable(context, resourceID)
    } catch (e: Resources.NotFoundException) {
        ContextCompat.getDrawable(context, R.drawable.image_placeholder)
    }

    Glide.with(context)
        .load(flagDrawable)
        .centerCrop()
        .transform(CircleCrop())
        .into(this)
}