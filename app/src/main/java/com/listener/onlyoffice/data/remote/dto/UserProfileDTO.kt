package com.listener.onlyoffice.data.remote.dto

data class UserProfileDTO(
    val id: String,
    val displayName: String,
    val avatar: String?,
    val email: String
)