package ru.mangotest.data.remote.api.model.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val avatars: Avatars
)
