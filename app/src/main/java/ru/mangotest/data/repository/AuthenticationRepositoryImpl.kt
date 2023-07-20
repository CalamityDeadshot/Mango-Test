package ru.mangotest.data.repository

import kotlinx.coroutines.flow.Flow
import ru.mangotest.core.Resource
import ru.mangotest.core.ResponseHandler
import ru.mangotest.data.local.auth_state.AuthState
import ru.mangotest.data.local.auth_state.toAuthState
import ru.mangotest.data.remote.api.AuthenticationApi
import ru.mangotest.data.remote.api.model.AuthResultDto
import ru.mangotest.data.remote.api.model.RefreshTokenRequest
import ru.mangotest.data.remote.api.model.UserAuthCode
import ru.mangotest.data.remote.api.model.UserPhone
import ru.mangotest.data.remote.api.model.UserRegistrationRequest
import ru.mangotest.domain.local.AuthStateStorage
import ru.mangotest.domain.repository.AuthenticationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor(
    private val authStateStorage: AuthStateStorage,
    private val handler: ResponseHandler,
    private val authApi: AuthenticationApi
): AuthenticationRepository {

    override val authState: Flow<AuthState?> = authStateStorage.authState

    override suspend fun registerUser(request: UserRegistrationRequest) = handler {
        authApi.registerUser(request)
    }.also { resource ->
        resource.handle(
            onSuccess = {
                authStateStorage.updateAuthState(it.toAuthState())
            }
        )
    }

    override suspend fun requestAuthCode(phone: UserPhone) = handler {
        authApi.sendAuthenticationCode(phone)
    }

    override suspend fun checkAuthCode(authCode: UserAuthCode): Resource<AuthResultDto> = handler {
        authApi.checkAuthCode(authCode)
    }.also { resource ->
        resource.handle(
            onSuccess = {
                if (it.doesUserExist) {
                    authStateStorage.updateAuthState(it.toAuthState())
                }
            }
        )
    }

    override suspend fun requestTokenRefreshment(refreshToken: RefreshTokenRequest) = handler {
        authApi.refreshAccessToken(refreshToken)
    }.also { resource ->
        resource.handle(
            onSuccess = {
                authStateStorage.updateAuthState(it.toAuthState())
            }
        )
    }

    override suspend fun checkAuthentication() = handler {
        authApi.checkAuthentication()
    }

    override suspend fun endSession() =
        authStateStorage.deleteAuthState()

}