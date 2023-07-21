package ru.mangotest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mangotest.core.Resource
import ru.mangotest.data.remote.api.model.user.EditUserInfoRequest
import ru.mangotest.data.remote.api.model.user.EditUserInfoResponse
import ru.mangotest.data.remote.api.model.user.ProfileData

interface UserRepository {

    fun getUserData(): Flow<ProfileData?>

    suspend fun editUserData(data: EditUserInfoRequest): Resource<EditUserInfoResponse>

    suspend fun updateUserData(): Resource<ProfileData>
}