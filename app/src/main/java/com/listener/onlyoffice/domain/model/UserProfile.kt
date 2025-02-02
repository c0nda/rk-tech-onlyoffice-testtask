package com.listener.onlyoffice.domain.model

data class UserProfile(
    val id: String,
    val displayName: String,
    val avatar: String?,
    val email: String
)
