package ru.mangotest.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.mangotest.data.remote.api.model.AuthResultDto
import ru.mangotest.data.remote.api.model.RefreshTokenDto
import ru.mangotest.data.remote.api.model.RefreshTokenRequest
import ru.mangotest.data.remote.api.model.UserAuthCode
import ru.mangotest.data.remote.api.model.UserPhone
import ru.mangotest.data.remote.api.model.UserPhoneDto
import ru.mangotest.data.remote.api.model.UserRegistrationDto
import ru.mangotest.data.remote.api.model.UserRegistrationRequest

interface AuthenticationApi {

    @POST("users/register/")
    suspend fun registerUser(
        @Body request: UserRegistrationRequest
    ): UserRegistrationDto

    @POST("users/send-auth-code/")
    suspend fun sendAuthenticationCode(
        @Body phoneRequest: UserPhone
    ): UserPhoneDto

    @POST("users/check-auth-code/")
    suspend fun checkAuthCode(
        @Body authCode: UserAuthCode
    ): AuthResultDto

    @POST("users/refresh-token/")
    suspend fun refreshAccessToken(
        @Body refreshToken: RefreshTokenRequest
    ): RefreshTokenDto

    @GET("users/check-jwt/")
    suspend fun checkAuthentication(): Response<Unit>
}