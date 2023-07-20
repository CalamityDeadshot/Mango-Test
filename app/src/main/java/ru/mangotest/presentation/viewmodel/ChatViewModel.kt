package ru.mangotest.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import ru.mangotest.domain.repository.MessagesRepository
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    savedState: SavedStateHandle,
    repo: MessagesRepository
): ViewModel() {
    private val chatId: String = savedState["id"]!!

    val messages = repo.getLatestMessages(
        chatId = chatId,
        limit = 10
    )
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}