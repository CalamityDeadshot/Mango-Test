package ru.mangotest.domain.local

import ru.mangotest.data.local.messages.model.Chat
import ru.mangotest.data.local.messages.model.Message

interface MessagesDataSource {

    suspend fun getChats(): List<Chat>

    suspend fun getLatestMessages(
        chatId: String,
        limit: Int
    ): List<Message>

    suspend fun sendMessage(
        chatId: String,
        message: String
    )
}