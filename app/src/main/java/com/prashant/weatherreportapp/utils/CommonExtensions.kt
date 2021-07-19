package com.prashant.weatherreportapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View

/**
 * @Author: Prshant G. Gupta
 * @Date: 18-07-2021
 */


/**
 * status whether Internet Connection available or not
 */
fun Context.checkInternetConnection(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

fun Boolean.visibleInvisible(): Int {
    return if (this) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Double.celToFah(): Double {
    return this * 9.0f / 5.0f + 32.0f
}

fun Double.kmToMiles(): Double {
    return this / 1.6f
}
