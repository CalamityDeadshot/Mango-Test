package ru.mangotest.presentation.util

sealed interface UiEvent {
    data class Navigate(val route: String): UiEvent
}