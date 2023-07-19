package ru.mangotest.domain.local

import kotlinx.coroutines.flow.Flow
import ru.mangotest.data.local.AuthState

interface AuthStateStorage {

    val hasTokenExpired: Boolean

    val authState: Flow<AuthState?>

    suspend fun updateAuthState(
        authState: AuthState
    )

    suspend fun deleteAuthState()
}