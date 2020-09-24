package com.imran.cavista.network.intercepter

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by imran on 2020-09-24.
 */
class NetworkInterceptor(context: Context) : Interceptor {
    private val appContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val request: Request
        //TODO: work here
        request = request().newBuilder()
            .cacheControl(cc)
            .build()
        proceed(request)
    }
}
