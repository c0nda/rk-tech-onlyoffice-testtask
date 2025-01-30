package com.listener.onlyoffice.data

import com.listener.onlyoffice.data.remote.dto.AccessTokenDTO
import com.listener.onlyoffice.domain.model.AccessToken

fun AccessTokenDTO.toDomain() = AccessToken(
    token = token
)