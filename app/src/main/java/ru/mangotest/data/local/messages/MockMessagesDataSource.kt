package ru.mangotest.data.local.messages

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import ru.mangotest.data.local.messages.model.Chat
import ru.mangotest.data.local.messages.model.ChatEntity
import ru.mangotest.data.local.messages.model.Message
import ru.mangotest.domain.local.AuthStateStorage
import ru.mangotest.domain.local.MessagesDataSource
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockMessagesDataSource @Inject constructor(
    private val authStateStorage: AuthStateStorage
) : MessagesDataSource {

    private val currentUserId: Int = runBlocking {
        authStateStorage.authState.first()!!.userId!!
    }

    private val chats = listOf(
        ChatEntity(
            id = UUID.randomUUID().toString(),
            companionId = 69
        ),
        ChatEntity(
            id = UUID.randomUUID().toString(),
            companionId = 420
        )
    )

    private val messages = mutableMapOf(
        chats[0].id to mutableListOf(
            Message(
                content = "Привет, MANGO FZCO! Попробуйте свайпнуть вверх в чате",
                sentAt = "2023-07-20T18:16:44+0000",
                isRead = true,
                authorId = 69
            ),
            Message(
                content = "Кринжанул",
                sentAt = "2023-07-20T18:17:44+0000",
                isRead = true,
                authorId = currentUserId
            )
        )
    )

    init {
        messages[chats[1].id] = (0..20).map {
            Message(
                content = "Lorem ipsum $it",
                sentAt = "2023-${(it + 1).toString().padStart(2, '0')}-20T18:17:44+0000",
                isRead = true,
                authorId = if (it % 2 == 0) currentUserId else 420
            )
        } as MutableList<Message>
    }

    override suspend fun getChats(): List<Chat> =
        chats.map {
            Chat(
                id = it.id,
                companionId = it.companionId,
                lastMessage = messages[it.id]!!.maxBy { it.sentAt }
            )
        }

    override suspend fun getLatestMessages(chatId: String, limit: Int): List<Message> =
        messages[chatId]!!.takeLast(limit)

    override suspend fun sendMessage(chatId: String, message: String) {
        messages[chatId]?.add(
            Message(
                content = message,
                sentAt = nowAsIso8601(),
                isRead = false,
                authorId = currentUserId
            )
        )
    }

    private fun Date.toIsoString(): String {
        val ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val dateFormat: DateFormat = SimpleDateFormat(ISO_8601_24H_FULL_FORMAT, Locale.getDefault())
        return dateFormat.format(this)
    }

    private fun nowAsIso8601() = Date().toIsoString()
}