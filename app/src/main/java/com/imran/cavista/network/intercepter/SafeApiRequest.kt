package com.imran.cavista.network.intercepter

import com.imran.cavista.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

/**
 * Created by imran on 2020-09-24.
 */
abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                }
                message.append("\n")
            }
            message.append("Error Code: ${response.code()}")
            throw ApiException(message.toString(), response.code())
        }
    }
}
