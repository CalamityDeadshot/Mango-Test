package ru.mangotest.data.local

import kotlinx.serialization.Serializable
import ru.mangotest.data.remote.api.model.AuthResultDto
import ru.mangotest.data.remote.api.model.RefreshTokenDto

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

fun RefreshTokenDto.toAuthState() =
    AuthState(
        accessToken, refreshToken, userId
    )