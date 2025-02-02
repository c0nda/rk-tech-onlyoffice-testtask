package com.listener.onlyoffice.data.repository

import com.listener.onlyoffice.data.remote.RemoteDataSource
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.model.Page
import com.listener.onlyoffice.domain.model.UserProfile
import com.listener.onlyoffice.domain.repository.UserRepository
import com.listener.onlyoffice.utils.Request
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : UserRepository {

    override suspend fun doAuth(email: String, password: String): Flow<Request<AccessToken>> {
        return remoteDataSource.authenticate(email, password)
    }

    override suspend fun checkAuth(): Flow<Request<Boolean>> {
        return remoteDataSource.checkAuth()
    }

    override suspend fun getUserProfile(): Flow<Request<UserProfile>> {
        return remoteDataSource.getUserProfile()
    }

    override suspend fun logoutUser(): Flow<Request<Unit>> {
        return remoteDataSource.logoutUser()
    }

    override suspend fun getDocuments(): Flow<Request<Page>> {
        return remoteDataSource.getDocuments()
    }

    override suspend fun getFolderContent(id: Long): Flow<Request<Page>> {
        return remoteDataSource.getFolderContent(id)
    }

    override suspend fun getRooms(): Flow<Request<Page>> {
        return remoteDataSource.getRooms()
    }

    override suspend fun getTrash(): Flow<Request<Page>> {
        return remoteDataSource.getTrash()
    }
}