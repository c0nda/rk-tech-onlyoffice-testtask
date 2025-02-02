package com.listener.onlyoffice.data

import com.listener.onlyoffice.data.remote.dto.CheckAuthDTO
import com.listener.onlyoffice.data.remote.dto.DocumentsResponseDTO
import com.listener.onlyoffice.data.remote.dto.FileDTO
import com.listener.onlyoffice.data.remote.dto.FolderDTO
import com.listener.onlyoffice.data.remote.dto.ResponseTokenDTO
import com.listener.onlyoffice.data.remote.dto.ResponseUserProfileDTO
import com.listener.onlyoffice.data.remote.dto.UserProfileDTO
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.model.File
import com.listener.onlyoffice.domain.model.Folder
import com.listener.onlyoffice.domain.model.Page
import com.listener.onlyoffice.domain.model.UserProfile

fun ResponseTokenDTO.toDomain() = AccessToken(
    token = response.token
)

fun ResponseUserProfileDTO.toDomain() = UserProfile(
    id = response.id,
    displayName = response.displayName,
    avatar = response.avatar,
    email = response.email
)

fun CheckAuthDTO.toDomain()  = response

fun FileDTO.toDomain() = File(
    id = id,
    title = title
)

fun FolderDTO.toDomain() = Folder(
    id = id,
    title = title
)
fun DocumentsResponseDTO.toDomain() = Page(
    files = response.files.map { it.toDomain() },
    folders = response.folders.map { it.toDomain() }
)

