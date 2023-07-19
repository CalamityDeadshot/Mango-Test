@file:OptIn(ExperimentalMaterial3Api::class)

package ru.mangotest.presentation.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.mangotest.R
import ru.mangotest.core.countries.CountryData
import ru.mangotest.core.countries.countryNames
import ru.mangotest.core.countries.getLibCountries
import ru.mangotest.presentation.navigation.LocalNavController
import ru.mangotest.presentation.viewmodel.AuthViewModel

@Composable
fun CountrySelectionBottomSheet(
    viewModel: AuthViewModel
) {
    val navController = LocalNavController.current

    CountrySelectionBottomSheetContent(
        selectedCountry = viewModel.state.selectedCountry,
        onCountrySelected = {
            viewModel.onEvent(AuthenticationEvent.OnCountrySelected(it))
            navController.popBackStack()
        }
    )
}

@Composable
private fun CountrySelectionBottomSheetContent(
    selectedCountry: CountryData,
    onCountrySelected: (CountryData) -> Unit
) {
    val countries = remember {
        getLibCountries
    }

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Text(
                text = stringResource(R.string.select_country),
                style = MaterialTheme.typography.headlineMedium
            )
        }

        items(
            items = countries,
            key = {it.countryCode}
        ) {
            ListItem(
                modifier = Modifier.clickable {
                    onCountrySelected(it)
                },
                colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                leadingContent = {
                    Text(
                        text = it.countryEmoji,
                        fontSize = 32.sp
                    )
                },
                headlineText = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        countryNames[it.countryCode]?.let {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(it),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        if (it == selectedCountry) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                },
                supportingText = {

                    Text(
                        text = "(${it.countryPhoneCode})"
                    )
                }
            )
        }
    }
}