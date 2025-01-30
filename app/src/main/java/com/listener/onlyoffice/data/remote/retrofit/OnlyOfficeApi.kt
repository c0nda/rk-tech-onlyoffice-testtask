package com.listener.onlyoffice.data.remote.retrofit

import com.listener.onlyoffice.data.remote.dto.AccessTokenDTO
import com.listener.onlyoffice.data.remote.dto.UserAuthDataDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface OnlyOfficeApi {

    @POST("/api/2.0/authentication")
    suspend fun doAuth(@Body userAuthDataDTO: UserAuthDataDTO): AccessTokenDTO
}