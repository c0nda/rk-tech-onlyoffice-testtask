package com.listener.onlyoffice.data.remote.retrofit

import com.listener.onlyoffice.data.remote.dto.CheckAuthDTO
import com.listener.onlyoffice.data.remote.dto.ResponseTokenDTO
import com.listener.onlyoffice.data.remote.dto.ResponseUserProfileDTO
import com.listener.onlyoffice.data.remote.dto.UserAuthDataDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OnlyOfficeApi {

    @POST("api/2.0/authentication/")
    suspend fun doAuth(@Body userAuthDataDTO: UserAuthDataDTO): ResponseTokenDTO

    @GET("api/2.0/authentication/")
    suspend fun checkAuth(): CheckAuthDTO

    @GET("api/2.0/people/@self")
    suspend fun getProfile(): ResponseUserProfileDTO

    @POST("api/2.0/authentication/logout")
    suspend fun logout()
}