package com.petter.datasource.util

import android.content.Context
import com.petter.usecases.exceptions.ApiException
import com.petter.usecases.exceptions.NetworkException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class ApiInterceptor(private val apiKey: String, private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isConnected() || context.isAirplaneModeActive()) {
            throw NetworkException()
        }
        val original = chain.request()

        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (!response.isSuccessful && response.code != 200)
            throw ApiException()
        return response
    }
}