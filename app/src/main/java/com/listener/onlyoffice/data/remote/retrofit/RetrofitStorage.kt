package com.listener.onlyoffice.data.remote.retrofit

import com.listener.onlyoffice.data.remote.RemoteDataSource
import com.listener.onlyoffice.data.remote.dto.UserAuthDataDTO
import com.listener.onlyoffice.data.toDomain
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.model.Page
import com.listener.onlyoffice.domain.model.UserProfile
import com.listener.onlyoffice.utils.Request
import com.listener.onlyoffice.utils.RequestUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RetrofitStorage @Inject constructor(
    private val onlyOfficeApi: OnlyOfficeApi
) : RemoteDataSource {

    override suspend fun authenticate(email: String, password: String): Flow<Request<AccessToken>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.doAuth(
                UserAuthDataDTO(
                    email = email,
                    password = password
                )
            ).toDomain()
        }
    }

    override suspend fun checkAuth(): Flow<Request<Boolean>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.checkAuth().toDomain()
        }
    }

    override suspend fun getUserProfile(): Flow<Request<UserProfile>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.getProfile().toDomain()
        }
    }

    override suspend fun logoutUser(): Flow<Request<Unit>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.logout()
        }
    }

    override suspend fun getDocuments(): Flow<Request<Page>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.getDocumentsSection().toDomain()
        }
    }

    override suspend fun getFolderContent(id: Long): Flow<Request<Page>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.getFolderContent(id).toDomain()
        }
    }

    override suspend fun getRooms(): Flow<Request<Page>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.getRooms().toDomain()
        }
    }

    override suspend fun getTrash(): Flow<Request<Page>> {
        return RequestUtils.requestFlow {
            onlyOfficeApi.getTrash().toDomain()
        }
    }
}