package com.listener.onlyoffice.data.remote.retrofit

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

object HostSelectionInterceptor : Interceptor {

    @Volatile
    private var host: String? = null

    fun setNewHost(newHost: String) {
        host = newHost
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (host != null) {
            val newUrl = HttpUrl.parse(host!!)
            request = request.newBuilder()
                .url(newUrl!!)
                .build()
        }
        return chain.proceed(request)
    }
}