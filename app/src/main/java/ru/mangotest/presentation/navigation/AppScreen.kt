package ru.mangotest.presentation.navigation

sealed class AppScreen(
    val route: String
) {
    object Authentication: AppScreen("authentication") {
        const val CodeSelectionBottomSheet = "authentication/cod_selection"
    }
}
