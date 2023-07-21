package ru.mangotest.presentation.viewmodel.state.auth

import ru.mangotest.data.remote.api.model.user.ProfileData

data class EditModeState(
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?
) {
    companion object {
        fun fromUserData(data: ProfileData?) =
            EditModeState(
                birthday = data?.birthday,
                city = data?.city,
                vk = data?.vk,
                instagram = data?.instagram,
                status = data?.status
            )
    }
}
