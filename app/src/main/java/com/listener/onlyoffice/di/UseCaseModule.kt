package com.listener.onlyoffice.di

import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.domain.usecase.AuthenticateUserUseCase
import com.listener.onlyoffice.domain.usecase.CheckUserAuthenticationUseCase
import com.listener.onlyoffice.domain.usecase.GetUserProfileUseCase
import com.listener.onlyoffice.domain.usecase.LogoutUserUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(userRepository: UserRepository) =
        AuthenticateUserUseCase(userRepository)

    @Provides
    @Singleton
    fun provideCheckUserAuthenticationUseCase(userRepository: UserRepository) =
        CheckUserAuthenticationUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetUserProfileUseCase(userRepository: UserRepository) =
        GetUserProfileUseCase(userRepository)

    @Provides
    @Singleton
    fun provideLogoutUserUseCase(userRepository: UserRepository) =
        LogoutUserUseCase(userRepository)
}