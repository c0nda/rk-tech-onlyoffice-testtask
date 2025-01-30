package com.listener.onlyoffice.domain.repository

import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun doAuth(email: String, password: String): Flow<Request<AccessToken>>
}