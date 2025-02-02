package com.listener.onlyoffice.data

import com.listener.onlyoffice.data.remote.dto.CheckAuthDTO
import com.listener.onlyoffice.data.remote.dto.ResponseTokenDTO
import com.listener.onlyoffice.data.remote.dto.ResponseUserProfileDTO
import com.listener.onlyoffice.data.remote.dto.UserProfileDTO
import com.listener.onlyoffice.domain.model.AccessToken
import com.listener.onlyoffice.domain.model.UserProfile

fun ResponseTokenDTO.toDomain() = AccessToken(
    token = response.token
)

fun ResponseUserProfileDTO.toDomain() = UserProfile(
    id = response.id,
    displayName = response.displayName,
    avatar = response.avatar,
    email =response.email
)

fun CheckAuthDTO.toDomain()  = response