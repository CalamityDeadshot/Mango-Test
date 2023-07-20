package ru.mangotest.data.remote

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import ru.mangotest.core.Resource
import ru.mangotest.core.ResponseHandler
import ru.mangotest.data.local.auth_state.toAuthState
import ru.mangotest.data.remote.api.AuthenticationApi
import ru.mangotest.data.remote.api.model.RefreshTokenDto
import ru.mangotest.data.remote.api.model.RefreshTokenRequest
import ru.mangotest.domain.local.AuthStateStorage

class TokenRefreshmentService(
    private val authenticationApi: AuthenticationApi,
    private val handler: ResponseHandler
) {

    var freshTokens: Deferred<Resource<RefreshTokenDto>>? = null
        private set

    suspend fun requestFreshTokens(
        authStateStorage: AuthStateStorage
    ) = withContext(Dispatchers.IO) {
        freshTokens = async {
            handler {
                authenticationApi.refreshAccessToken(
                    refreshToken = RefreshTokenRequest(
                        refreshToken = authStateStorage.authState.first()!!.refreshToken!!
                    ),
                    bearer = "Bearer ${authStateStorage.authState.first()!!.accessToken!!}"
                )
            }
        }
    }

    suspend inline fun withFreshTokens(
        authStateStorage: AuthStateStorage,
        block: (accessToken: String?, exception: Throwable?) -> Unit
    ) {
        val authState = authStateStorage.authState.first() ?: kotlin.run {
            block(null, IllegalStateException("Auth state does not exist"))
            return
        }
        val hasTokenExpired = authState.accessTokenPayload?.hasTokenExpired ?: kotlin.run {
            block(null, IllegalStateException("Access token is not present"))
            return
        }

        if (!hasTokenExpired) {
            block(authState.accessToken!!, null)
            return
        }

        if (freshTokens?.isActive != true) {
            requestFreshTokens(authStateStorage)
        }

        val newTokens = freshTokens?.await()

        newTokens?.handle(
            onSuccess = {
                authStateStorage.updateAuthState(it.toAuthState())
                block(it.accessToken, null)

            }
        )
    }
}