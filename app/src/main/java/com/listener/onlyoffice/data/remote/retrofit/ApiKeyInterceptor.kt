package com.listener.onlyoffice.data.remote.retrofit

import okhttp3.Interceptor
import okhttp3.Response

object ApiKeyInterceptor : Interceptor {

    @Volatile
    private var apiKey: String? = null

    fun setApiKey(key: String) {
        apiKey = key
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (apiKey != null) {
            request = request.newBuilder()
                .addHeader("asc_auth_key", apiKey!!)
                .build()
        }
        return chain.proceed(request)
    }
}