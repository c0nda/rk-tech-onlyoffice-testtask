package com.listener.onlyoffice.data.remote.retrofit

import com.listener.onlyoffice.data.remote.dto.CheckAuthDTO
import com.listener.onlyoffice.data.remote.dto.DocumentsResponseDTO
import com.listener.onlyoffice.data.remote.dto.ResponseTokenDTO
import com.listener.onlyoffice.data.remote.dto.ResponseUserProfileDTO
import com.listener.onlyoffice.data.remote.dto.UserAuthDataDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface OnlyOfficeApi {

    @POST("api/2.0/authentication/")
    suspend fun doAuth(@Body userAuthDataDTO: UserAuthDataDTO): ResponseTokenDTO

    @GET("api/2.0/authentication/")
    suspend fun checkAuth(): CheckAuthDTO

    @GET("api/2.0/people/@self")
    suspend fun getProfile(): ResponseUserProfileDTO

    @POST("api/2.0/authentication/logout")
    suspend fun logout()

    @GET("api/2.0/files/@my")
    suspend fun getDocumentsSection(
        @Query("searchInContent") searchInContent: Boolean = false,
        @Query("withsubfolders") withSubfolders: Boolean = false,
    ): DocumentsResponseDTO

    @GET("api/2.0/files/{folderId}")
    suspend fun getFolderContent(@Path("folderId") id: Long): DocumentsResponseDTO

    @GET("api/2.0/files/rooms")
    suspend fun getRooms(): DocumentsResponseDTO

    @GET("api/2.0/files/@trash")
    suspend fun getTrash(): DocumentsResponseDTO
}