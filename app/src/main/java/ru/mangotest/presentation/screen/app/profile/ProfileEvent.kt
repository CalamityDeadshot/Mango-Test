package ru.mangotest.presentation.screen.app.profile

import android.net.Uri

sealed interface ProfileEvent {
    object OnUpdate: ProfileEvent
    object OnEdit: ProfileEvent
    object OnConfirmEdit: ProfileEvent
    object OnCancelEdit: ProfileEvent

    data class OnCityChanged(val city: String): ProfileEvent
    data class OnVkChanged(val vk: String): ProfileEvent
    data class OnInstagramChanged(val inst: String): ProfileEvent
    data class OnStatusChanged(val status: String): ProfileEvent
    data class OnPhotoPicked(
        val uri: Uri?,
        val base64Factory: () -> String?
    ): ProfileEvent
}