package com.listener.onlyoffice.di

import android.content.Context
import com.listener.onlyoffice.data.local.DataStoreManager
import com.listener.onlyoffice.data.remote.RemoteDataSource
import com.listener.onlyoffice.data.remote.retrofit.RetrofitStorage
import com.listener.onlyoffice.data.repository.UserRepositoryImpl
import com.listener.onlyoffice.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindRemoteDataSource(retrofitStorage: RetrofitStorage): RemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideDataStoreManager(context: Context) = DataStoreManager(context)
    }
}