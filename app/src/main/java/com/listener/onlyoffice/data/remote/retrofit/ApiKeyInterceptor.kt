package com.listener.onlyoffice.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    private var apiKey: String? = null
    fun setApiKey(key: String?) {
        apiKey = key
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (apiKey != null) {
            request = request.newBuilder()
                .addHeader("Authorization", apiKey!!)
                .build()
        }
        apiKey = null
        return chain.proceed(request)
    }
}