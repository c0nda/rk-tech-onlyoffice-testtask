package com.listener.onlyoffice.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserAuthDataDTO(
    @SerializedName("userName") val email: String,
    val password: String
)
