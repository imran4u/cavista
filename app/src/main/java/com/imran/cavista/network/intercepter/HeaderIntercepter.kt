package com.imran.cavista.network.intercepter

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by imran on 2020-09-24.
 */
class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val builder: Request.Builder = request().newBuilder()
            .addHeader("Application-Name", "android")
        proceed(builder.build())
    }

}
