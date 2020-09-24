package com.imran.cavista.network

import com.imran.cavista.model.ImagesResponse
import com.imran.cavista.network.intercepter.HeaderInterceptor
import com.imran.cavista.network.intercepter.NetworkInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by imran on 2020-09-24.
 */
const val BASE_URL = "https://api.imgur.com/"

//https://api.imgur.com/3/gallery/search/1?q=vanilla
interface ApiRequestService {

    @GET("3/gallery/search/{page}")
    suspend fun getImages(
        @Path("page") page: Int,
        @Query("q") searchValue: String??
    ): Response<ImagesResponse>

    companion object {
        operator fun invoke(
            networkInterceptor: NetworkInterceptor,
            headerInterceptor: HeaderInterceptor,
        ): ApiRequestService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY


            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(networkInterceptor)
                .addInterceptor(logging)
                .build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()
                .create(ApiRequestService::class.java)
        }
    }
}
