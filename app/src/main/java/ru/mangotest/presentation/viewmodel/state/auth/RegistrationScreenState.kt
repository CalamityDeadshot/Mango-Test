package ru.mangotest.presentation.viewmodel.state.auth

import ru.mangotest.core.UiText

data class RegistrationScreenState(
    val name: String = "",
    val username: String = "",

    val nameError: UiText? = null,
    val usernameError: UiText? = null,

    val isRegistering: Boolean = false
)
