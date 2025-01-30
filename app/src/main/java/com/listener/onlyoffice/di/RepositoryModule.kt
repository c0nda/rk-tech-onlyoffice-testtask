package com.listener.onlyoffice.di

import com.listener.onlyoffice.data.remote.RemoteDataSource
import com.listener.onlyoffice.data.remote.retrofit.RetrofitStorage
import com.listener.onlyoffice.data.repository.AuthRepositoryImpl
import com.listener.onlyoffice.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindRemoteDataSource(retrofitStorage: RetrofitStorage): RemoteDataSource
}