package ru.mangotest.core.countries

import android.content.Context
import android.telephony.TelephonyManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberCountryCode(): String {
    val context = LocalContext.current
    return remember {
        context.countryCode
    }
}

val Context.countryCode: String
    get() = getSystemService(TelephonyManager::class.java).simCountryIso