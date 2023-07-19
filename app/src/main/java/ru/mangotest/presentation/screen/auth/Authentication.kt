@file:OptIn(ExperimentalMaterial3Api::class)

package ru.mangotest.presentation.screen.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.mangotest.R
import ru.mangotest.core.countries.numberHint
import ru.mangotest.presentation.components.MaskVisualTransformation
import ru.mangotest.presentation.navigation.AppScreen
import ru.mangotest.presentation.navigation.LocalNavController
import ru.mangotest.presentation.screen.auth.components.RegistrationCodeField
import ru.mangotest.presentation.util.collectIntoSnackbar
import ru.mangotest.presentation.viewmodel.AuthViewModel
import ru.mangotest.presentation.viewmodel.state.auth.AuthenticationScreenState

@Composable
fun Authentication(
    viewModel: AuthViewModel
) {
    val navController = LocalNavController.current

    val snackbarHostState = remember { SnackbarHostState() }
    viewModel.uiMessages.collectIntoSnackbar(snackbarHostState)

    AuthenticationContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onOpenCodeSelection = {
            navController.navigate(AppScreen.Authentication.CodeSelectionBottomSheet)
        },
        snackbarHostState = snackbarHostState
    )
}

@Composable
private fun AuthenticationContent(
    state: AuthenticationScreenState,
    onEvent: (AuthenticationEvent) -> Unit,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    onOpenCodeSelection: () -> Unit
) {

    val phoneNumber = state.phoneNumber

    val context = LocalContext.current

    val numberHint = remember(state.selectedCountry) {
        numberHint[state.selectedCountry.countryCode]?.let {
            context.getString(it)
        }
    }

    val maxPhoneNumberLength = remember(numberHint) {
        numberHint?.count { it.isDigit() } ?: 15 // Maximum phone number length as imposed by ITU
    }

    val visualTransformation = remember(numberHint) {
        if (numberHint == null) VisualTransformation.None
        else {
            MaskVisualTransformation(
                mask = numberHint.replace("\\d".toRegex(), "#")
            )
        }
    }

    val codeInputFocusRequester = remember { FocusRequester() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.please_sign_in))
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = it.calculateTopPadding() + it.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {

                    Row(
                        modifier = Modifier
                            .defaultMinSize(minHeight = 58.dp)
                            .background(
                                color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                                shape = MaterialTheme.shapes.extraSmall
                            )
                            .clip(MaterialTheme.shapes.extraSmall)
                            .clickable(
                                role = Role.Button,
                                onClick = onOpenCodeSelection,
                                enabled = !state.hasRequestedCode
                            )
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .alpha(if (state.hasRequestedCode) .6f else 1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "${state.selectedCountry.countryEmoji} ${state.selectedCountry.countryPhoneCode}")
                    }

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            if (it.length <= maxPhoneNumberLength) {
                                onEvent(AuthenticationEvent.PhoneNumberChanged(it))
                            }
                        },
                        visualTransformation = visualTransformation,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Send
                        ),
                        maxLines = 1,
                        singleLine = true,
                        keyboardActions = KeyboardActions {
                            onEvent(AuthenticationEvent.OnRequestCode)
                        },
                        label = {
                            Text(text = stringResource(R.string.phone_number))
                        },
                        placeholder = {
                            numberHint?.let {
                                Text(text = it)
                            }
                        },
                        enabled = !state.hasRequestedCode
                    )
                }
                AnimatedVisibility(visible = !state.isRequestingCode && !state.hasRequestedCode) {
                    TextButton(
                        onClick = { onEvent(AuthenticationEvent.OnRequestCode) },
                        enabled = !state.isRequestingCode
                    ) {
                        if (state.isRequestingCode) {
                            CircularProgressIndicator()
                        } else {
                            Text(text = stringResource(R.string.reqest_auth_code))
                        }
                    }
                }

                AnimatedVisibility(
                    visible = state.hasRequestedCode,
                    enter = fadeIn() + slideInVertically { 2 * it }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(R.string.code_sent))

                        Spacer(modifier = Modifier.height(16.dp))

                        RegistrationCodeField(
                            modifier = Modifier
                                .focusRequester(codeInputFocusRequester),
                            text = state.authenticationCode,
                            codeLength = 6,
                            onTextChange = {
                                onEvent(AuthenticationEvent.AuthCodeChanged(it))
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                            ),
                            enabled = !state.isCheckingAuthCode
                        )
                    }

                    SideEffect {
                        codeInputFocusRequester.requestFocus()
                    }
                }
            }

        }

    }
}

