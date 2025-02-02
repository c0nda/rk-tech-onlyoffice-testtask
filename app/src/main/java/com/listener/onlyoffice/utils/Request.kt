package com.listener.onlyoffice.utils

sealed class Request<T> {

    class Init<T> : Request<T>()
    class Loading<T> : Request<T>()
    data class Success<T>(val data: T) : Request<T>()
    data class Error<T>(val error: Throwable?) : Request<T>()
}