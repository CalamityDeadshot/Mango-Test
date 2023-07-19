package ru.mangotest.presentation.viewmodel.state.auth

import ru.mangotest.core.countries.CountryData

data class AuthenticationScreenState(
    val selectedCountry: CountryData,
    val phoneNumber: String = "",
    val hasRequestedCode: Boolean = false,
    val authenticationCode: String = ""
)