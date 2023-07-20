package ru.mangotest.data.local.messages.model

import ru.mangotest.data.remote.api.model.user.ProfileData

data class ChatEntity(
    val id: String,
    val companionId: Int,
)


data class Chat(
    val id: String,
    val companion: ProfileData,
    val lastMessage: Message
)