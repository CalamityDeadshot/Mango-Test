package ru.mangotest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost

@Composable
fun AppNavigation() {
    val navController = LocalNavController.current

    NavHost(
        navController = navController,
        startDestination = ""
    ) {
        messages()

        profile()
    }
}

fun NavGraphBuilder.messages() {

}

fun NavGraphBuilder.profile() {

}