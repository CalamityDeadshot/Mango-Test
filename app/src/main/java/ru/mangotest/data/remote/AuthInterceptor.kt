package ru.mangotest.data.remote

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import ru.mangotest.domain.local.AuthStateStorage
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authStateStorage: AuthStateStorage
): Interceptor {

    private val authenticationPaths = setOf(
        "/api/v1/users/register",
        "/api/v1/users/send-auth-code",
        "/api/v1/users/check-auth-code"
    )

    override fun intercept(chain: Interceptor.Chain) =
        if (chain.request().url.encodedPath in authenticationPaths) {
            chain.proceed(chain.request())
        } else {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${getAccessToken()}")
                    .build()
            )
        }

    private fun getAccessToken() = runBlocking {
        authStateStorage.authState.first()?.accessToken
    }
}