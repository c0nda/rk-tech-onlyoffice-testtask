package com.listener.onlyoffice.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.listener.onlyoffice.data.remote.retrofit.HostSelectionInterceptor
import com.listener.onlyoffice.data.remote.retrofit.OnlyOfficeApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideHostSelectionInterceptor(): HostSelectionInterceptor =
        HostSelectionInterceptor

    @Provides
    @Singleton
    fun provideOkHttpClient(hostSelectionInterceptor: HostSelectionInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(hostSelectionInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://localhost/")
        .build()

    @Provides
    @Singleton
    fun provideOnlyOfficeApi(retrofit: Retrofit): OnlyOfficeApi =
        retrofit.create(OnlyOfficeApi::class.java)
}