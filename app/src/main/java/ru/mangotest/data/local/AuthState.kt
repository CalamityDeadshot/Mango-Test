package ru.mangotest.data.local

import kotlinx.serialization.Serializable
import ru.mangotest.data.remote.api.model.AuthResultDto

@Serializable
data class AuthState(
    val accessToken: String,
    val refreshToken: String,
    val userId: Int
)

fun AuthResultDto.toAuthState() =
    AuthState(
        accessToken, refreshToken, userId
    )
