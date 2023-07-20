package ru.mangotest.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.mangotest.core.Resource
import ru.mangotest.data.local.messages.model.Message

interface MessagesRepository {

    fun getLatestMessages(
        chatId: String,
        limit: Int
    ): Flow<List<Message>>

    suspend fun sendMessage(
        chatId: String,
        text: String
    ): Resource<Unit>
}