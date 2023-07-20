package ru.mangotest.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import ru.mangotest.core.ResponseHandler
import ru.mangotest.data.local.messages.model.Chat
import ru.mangotest.domain.local.MessagesDataSource
import ru.mangotest.domain.repository.MessagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessagesRepositoryImpl @Inject constructor(
    private val dataSource: MessagesDataSource,
    private val handler: ResponseHandler
) : MessagesRepository {
    override fun getChats(): Flow<List<Chat>> = flow {
        emit(dataSource.getChats())
    }

    // In reality this would be a WebSocket connection
    override fun getLatestMessages(chatId: String, limit: Int) = flow {
        while (true) {
            delay(300)
            emit(dataSource.getLatestMessages(chatId, limit))
        }
    }.distinctUntilChanged()

    override suspend fun sendMessage(chatId: String, text: String) = handler {
        dataSource.sendMessage(chatId, text)
    }
}