package ru.mangotest.data.remote

import okhttp3.Interceptor
import javax.inject.Inject

class AuthInterceptor @Inject constructor(

): Interceptor {

    private val authenticationPaths = listOf("")

    override fun intercept(chain: Interceptor.Chain) =
        if (chain.request().url.encodedPath in authenticationPaths) {
            chain.proceed(chain.request())
        } else {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer token")
                    .build()
            )
        }
}