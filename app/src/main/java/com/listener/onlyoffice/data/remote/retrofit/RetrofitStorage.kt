package com.listener.onlyoffice.data.remote.retrofit

import com.listener.onlyoffice.data.remote.RemoteDataSource
import com.listener.onlyoffice.data.remote.dto.UserAuthDataDTO
import com.listener.onlyoffice.data.toDomain
import com.listener.onlyoffice.domain.model.AccessToken
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
}