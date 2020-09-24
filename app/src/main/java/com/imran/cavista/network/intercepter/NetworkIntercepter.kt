package com.imran.cavista.network.intercepter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.imran.cavista.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by imran on 2020-09-24.
 */
class NetworkInterceptor(context: Context) : Interceptor {
    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        if (!isInternetAvailable()) {
            throw NoInternetException("PLease check your internet connection")
        }
        proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result

    }
}
