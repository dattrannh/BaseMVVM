package com.vn.basemvvm.utils.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import android.net.NetworkCapabilities
import com.google.gson.reflect.TypeToken
import java.lang.ref.WeakReference

class NetworkListener(context: Context) : LiveData<Boolean>() {

    private val connectivityManager: ConnectivityManager = context.applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private var weakReference: WeakReference<Context>? = WeakReference(context.applicationContext)
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)
        }
        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) request()
        else {
            updateConnection()
            weakReference?.get()?.registerReceiver(networkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            weakReference?.get()?.unregisterReceiver(networkReceiver)
        }
        weakReference = null
    }

    private fun request() {
        val builder = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }
    }

    @Suppress("DEPRECATION")
    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnectedOrConnecting == true)
    }
}