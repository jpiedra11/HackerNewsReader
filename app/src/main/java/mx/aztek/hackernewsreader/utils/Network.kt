package mx.aztek.hackernewsreader.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object Network {
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected) {
            isConnected = true
        }
        return isConnected
    }
}
