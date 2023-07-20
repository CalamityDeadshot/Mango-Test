package ru.mangotest.presentation.screen.app.messages

sealed interface ChatEvent {
    data class OnTypeMessage(val text: String): ChatEvent
    object OnSendMessage: ChatEvent
}