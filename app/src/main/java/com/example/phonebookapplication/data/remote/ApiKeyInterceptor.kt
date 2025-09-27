package com.example.phonebookapplication.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()

        val requestWithApiKey = originalRequest.newBuilder()
            .header("ApiKey", apiKey)
            .method(originalRequest.method, originalRequest.body)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}
