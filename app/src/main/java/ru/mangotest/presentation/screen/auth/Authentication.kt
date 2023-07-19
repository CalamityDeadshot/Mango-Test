@file:OptIn(ExperimentalMaterial3Api::class)

package ru.mangotest.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.mangotest.R
import ru.mangotest.core.countries.numberHint
import ru.mangotest.presentation.components.PhoneNumberVisualTransformation
import ru.mangotest.presentation.navigation.AppScreen
import ru.mangotest.presentation.navigation.LocalNavController
import ru.mangotest.presentation.viewmodel.AuthViewModel
import ru.mangotest.presentation.viewmodel.state.auth.AuthenticationScreenState

@Composable
fun Authentication(
    viewModel: AuthViewModel
) {
    val navController = LocalNavController.current

    AuthenticationContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent,
        onOpenCodeSelection = {
            navController.navigate(AppScreen.Authentication.CodeSelectionBottomSheet)
        }
    )
}

@Composable
private fun AuthenticationContent(
    state: AuthenticationScreenState,
    onEvent: (AuthenticationEvent) -> Unit,
    onOpenCodeSelection: () -> Unit
) {

    val phoneNumber = state.phoneNumber

    Scaffold(
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
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                            onClick = onOpenCodeSelection
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "${state.selectedCountry.countryEmoji} ${state.selectedCountry.countryPhoneCode}")
                }

                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { onEvent(AuthenticationEvent.PhoneNumberChanged(it)) },
                    visualTransformation = PhoneNumberVisualTransformation(state.selectedCountry.countryCode),
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = false,
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Send
                    ),
                    maxLines = 1,
                    singleLine = true,
                    keyboardActions = KeyboardActions {

                    },
                    label = {
                        Text(text = stringResource(R.string.phone_number))
                    },
                    placeholder = {
                        numberHint[state.selectedCountry.countryCode]?.let {
                            Text(text = stringResource(it))
                        }
                    }
                )
            }
        }

    }
}

