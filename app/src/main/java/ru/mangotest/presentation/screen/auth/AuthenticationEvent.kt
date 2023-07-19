package ru.mangotest.presentation.screen.auth

import ru.mangotest.core.countries.CountryData

sealed interface AuthenticationEvent {
    data class PhoneNumberChanged(val phoneNumber: String): AuthenticationEvent
    data class OnCountrySelected(val country: CountryData): AuthenticationEvent
    object OnRequestCode: AuthenticationEvent

    data class AuthCodeChanged(val authCode: String): AuthenticationEvent
}