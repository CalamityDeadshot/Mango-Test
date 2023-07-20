package ru.mangotest.data.local.messages.model

data class ChatEntity(
    val id: String,
    val companionId: Int,
)


data class Chat(
    val id: String,
    val companionId: Int,
    val lastMessage: Message
)