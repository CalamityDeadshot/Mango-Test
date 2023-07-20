package ru.mangotest.domain.local

import kotlinx.coroutines.flow.Flow
import ru.mangotest.data.local.auth_state.AuthState

interface AuthStateStorage {

    val authState: Flow<AuthState?>

    suspend fun updateAuthState(
        authState: AuthState
    )

    suspend fun deleteAuthState()
}