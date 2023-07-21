package ru.mangotest.domain.local

import kotlinx.coroutines.flow.Flow
import ru.mangotest.data.remote.api.model.user.ProfileData

interface UserDataStorage {

    val user: Flow<ProfileData?>

    suspend fun updateUserData(
        userData: ProfileData
    )

    suspend fun deleteUserData()
}