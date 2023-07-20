package ru.mangotest.presentation.screen.auth

sealed interface RegistrationEvent {
    data class OnNameChanged(val name: String): RegistrationEvent
    data class OnUsernameChanged(val username: String): RegistrationEvent
    object OnRegister: RegistrationEvent
}