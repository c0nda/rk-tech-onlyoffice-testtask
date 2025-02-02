package com.listener.onlyoffice.di

import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.domain.usecase.AuthenticateUserUseCase
import com.listener.onlyoffice.domain.usecase.CheckUserAuthenticationUseCase
import com.listener.onlyoffice.domain.usecase.GetDocumentsUseCase
import com.listener.onlyoffice.domain.usecase.GetFolderContentUseCase
import com.listener.onlyoffice.domain.usecase.GetRoomsUseCase
import com.listener.onlyoffice.domain.usecase.GetTrashUseCase
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

    @Provides
    @Singleton
    fun provideGetDocumentsUseCase(userRepository: UserRepository) =
        GetDocumentsUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetFolderContentUseCase(userRepository: UserRepository) =
        GetFolderContentUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetRoomsUseCase(userRepository: UserRepository) =
        GetRoomsUseCase(userRepository)

    @Provides
    @Singleton
    fun provideGetTrashUseCase(userRepository: UserRepository) =
        GetTrashUseCase(userRepository)
}