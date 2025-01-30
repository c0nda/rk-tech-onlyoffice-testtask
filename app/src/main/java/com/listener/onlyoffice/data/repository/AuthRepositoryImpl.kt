package com.listener.onlyoffice.data.repository

import com.listener.onlyoffice.data.remote.RemoteDataSource
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.repository.AuthRepository
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : AuthRepository {

    override suspend fun doAuth(email: String, password: String): Flow<Request<AccessToken>> {
        return remoteDataSource.authenticate(email, password)
    }
}