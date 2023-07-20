package ru.mangotest.data.local

import kotlinx.serialization.Serializable
import ru.mangotest.data.remote.api.model.AuthResultDto
import ru.mangotest.data.remote.api.model.RefreshTokenDto
import ru.mangotest.data.remote.api.model.UserRegistrationDto

@Serializable
data class AuthState(
    val accessToken: String?,
    val refreshToken: String?,
    val userId: Int?,
    val isAuthorized: Boolean = false
) {
    companion object {
        val Empty = AuthState(
            accessToken = null,
            refreshToken = null,
            userId = null,
            isAuthorized = false
        )
    }
}

fun AuthResultDto.toAuthState() =
    AuthState(
        accessToken, refreshToken, userId, true
    )

fun RefreshTokenDto.toAuthState() =
    AuthState(
        accessToken, refreshToken, userId, true
    )

fun UserRegistrationDto.toAuthState() =
    AuthState(
        accessToken, refreshToken, userId
    )