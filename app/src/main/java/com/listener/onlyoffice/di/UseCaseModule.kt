package com.listener.onlyoffice.di

import com.listener.onlyoffice.domain.repository.AuthRepository
import com.listener.onlyoffice.domain.usecase.AuthenticateUserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(authRepository: AuthRepository) =
        AuthenticateUserUseCase(authRepository)
}