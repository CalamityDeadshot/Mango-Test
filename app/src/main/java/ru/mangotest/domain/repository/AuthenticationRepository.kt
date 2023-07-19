package ru.mangotest.domain.repository

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import ru.mangotest.core.Resource
import ru.mangotest.data.local.AuthState
import ru.mangotest.data.remote.api.model.AuthResultDto
import ru.mangotest.data.remote.api.model.RefreshTokenDto
import ru.mangotest.data.remote.api.model.RefreshTokenRequest
import ru.mangotest.data.remote.api.model.UserAuthCode
import ru.mangotest.data.remote.api.model.UserPhone
import ru.mangotest.data.remote.api.model.UserPhoneDto
import ru.mangotest.data.remote.api.model.UserRegistrationDto
import ru.mangotest.data.remote.api.model.UserRegistrationRequest

interface AuthenticationRepository {

    val authState: Flow<AuthState?>

    suspend fun registerUser(
        request: UserRegistrationRequest
    ): Resource<UserRegistrationDto>

    suspend fun requestAuthCode(
        phone: UserPhone
    ): Resource<UserPhoneDto>

    suspend fun checkAuthCode(
        authCode: UserAuthCode
    ): Resource<AuthResultDto>

    suspend fun requestTokenRefreshment(
        refreshToken: RefreshTokenRequest
    ): Resource<RefreshTokenDto>

    suspend fun checkAuthentication(): Resource<Response<Unit>>

    suspend fun endSession()
}