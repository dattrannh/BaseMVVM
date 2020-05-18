package com.vn.basemvvm.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.vn.basemvvm.App
import com.vn.basemvvm.utils.AppConfig
import com.vn.basemvvm.utils.rx.RxBus


object NetworkUtils {


    @JvmStatic
    @Suppress("DEPRECATION")
    fun isNetworkConnected(): Boolean {
        val cm = AppConfig.connectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.activeNetwork ?: return false
            val nc = cm.getNetworkCapabilities(networkCapabilities) ?: return false
            when {
                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val activeNetwork = cm.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

}