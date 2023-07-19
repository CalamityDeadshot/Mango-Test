package ru.mangotest.data.remote.api.model.user


import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequest(
    val name: String,
    val username: String,
    val birthday: String,
    val city: String,
    val vk: String,
    val instagram: String,
    val status: String,
    val avatar: Avatar
)