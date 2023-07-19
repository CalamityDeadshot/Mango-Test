package ru.mangotest.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.mangotest.core.countries.countryCode
import ru.mangotest.core.countries.countryDataMap
import ru.mangotest.core.countries.russia
import ru.mangotest.domain.repository.AuthenticationRepository
import ru.mangotest.presentation.screen.auth.AuthenticationEvent
import ru.mangotest.presentation.viewmodel.state.auth.AuthenticationScreenState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthenticationRepository,
    @ApplicationContext context: Context
): ViewModel() {
    val authState = repo.authState

    var state by mutableStateOf(
        AuthenticationScreenState(
            selectedCountry = countryDataMap[context.countryCode] ?: russia
        )
    )
        private set

    fun onEvent(event: AuthenticationEvent) = when (event) {
        is AuthenticationEvent.OnCountrySelected -> {
            state = state.copy(
                selectedCountry = event.country
            )
        }
        AuthenticationEvent.OnRequestCode -> TODO()
        is AuthenticationEvent.PhoneNumberChanged -> {
            state = state.copy(
                phoneNumber = event.phoneNumber
            )
        }
    }
}