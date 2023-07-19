package ru.mangotest.presentation.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.mangotest.core.UiText
import ru.mangotest.core.countries.countryCode
import ru.mangotest.core.countries.countryDataMap
import ru.mangotest.core.countries.russia
import ru.mangotest.data.remote.api.model.UserAuthCode
import ru.mangotest.data.remote.api.model.UserPhone
import ru.mangotest.domain.repository.AuthenticationRepository
import ru.mangotest.presentation.screen.auth.AuthenticationEvent
import ru.mangotest.presentation.viewmodel.state.auth.AuthenticationScreenState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthenticationRepository,
    @ApplicationContext context: Context // ugh
): ViewModel() {
    val authState = repo.authState

    var state by mutableStateOf(
        AuthenticationScreenState(
            selectedCountry = countryDataMap[context.countryCode] ?: russia
        )
    )
        private set

    private val _uiMessages = MutableSharedFlow<UiText>()
    val uiMessages = _uiMessages.asSharedFlow()

    fun onEvent(event: AuthenticationEvent) {
        when (event) {
            is AuthenticationEvent.OnCountrySelected -> {
                state = state.copy(
                    selectedCountry = event.country
                )
            }
            AuthenticationEvent.OnRequestCode -> {
                state = state.copy(
                    isRequestingCode = true
                )
                requestAuthCode()
            }
            is AuthenticationEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = event.phoneNumber
                )
            }
            is AuthenticationEvent.AuthCodeChanged -> {
                if (event.authCode.length == 6) {
                    checkAuthCode()
                }
                state = state.copy(
                    authenticationCode = event.authCode
                )
            }
        }
    }

    private fun requestAuthCode() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.requestAuthCode(
                UserPhone(
                    phone = "${state.selectedCountry.countryPhoneCode}${state.phoneNumber}"
                )
            ).handle(
                onSuccess = {
                    if (it.isSuccess) {
                        state = state.copy(
                            isRequestingCode = false,
                            hasRequestedCode = true
                        )
                    }
                },
                onError = {
                    _uiMessages.emit(it)
                }
            )
        }
    }

    private fun checkAuthCode() = viewModelScope.launch(Dispatchers.IO) {
        repo.checkAuthCode(
            UserAuthCode(
                code = state.authenticationCode,
                phone = state.fullPhoneNumber
            )
        ).handle(
            onSuccess = {

            },
            onError = {
                _uiMessages.emit(it)
                state = state.copy(
                    authenticationCode = ""
                )
            }
        )
    }
}