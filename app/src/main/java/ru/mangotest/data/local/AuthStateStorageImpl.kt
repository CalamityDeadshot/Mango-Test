package ru.mangotest.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.mangotest.domain.local.AuthStateStorage
import javax.inject.Inject


class AuthStateStorageImpl @Inject constructor(
    @ApplicationContext context: Context
): AuthStateStorage {

    private companion object {
        const val AUTH_STATE_PREFS = "auth_state_storage"
        val AUTH_STATE_KEY = stringPreferencesKey("auth_state")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = AUTH_STATE_PREFS
    )
    private val dataStore = context.dataStore

    private val Preferences.authState: AuthState?
        get() = this[AUTH_STATE_KEY]?.let {
            Json.Default.decodeFromString(it)
        }

    override val hasTokenExpired: Boolean
        get() = false

    override val authState: Flow<AuthState?>
        get() = dataStore.data.map { it.authState }

    override suspend fun updateAuthState(authState: AuthState) {
        dataStore.edit { prefs ->
            prefs[AUTH_STATE_KEY] = Json.Default.encodeToString(authState)
        }
    }

    override suspend fun deleteAuthState() {
        dataStore.edit {
            it.remove(AUTH_STATE_KEY)
        }
    }
}