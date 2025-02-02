package com.listener.onlyoffice.data.remote

import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.model.UserProfile
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun authenticate(email: String, password: String): Flow<Request<AccessToken>>

    suspend fun checkAuth(): Flow<Request<Boolean>>

    suspend fun getUserProfile(): Flow<Request<UserProfile>>

    suspend fun logoutUser(): Flow<Request<Unit>>
}