package com.listener.onlyoffice.data.remote.retrofit

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostSelectionInterceptor : Interceptor {

    private var host: String? = null
    private var scheme: String? = null

    fun setNewHost(newUrl: String?) {
        val url = newUrl?.toHttpUrlOrNull()
        scheme = url?.scheme
        host = url?.host
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (host != null && scheme != null) {
            val newUrl = request.url.newBuilder()
                .scheme(scheme!!)
                .host(host!!)
                .build()
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(request)
    }
}