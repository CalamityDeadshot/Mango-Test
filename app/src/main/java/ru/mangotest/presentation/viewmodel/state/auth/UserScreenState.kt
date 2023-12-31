package ru.mangotest.presentation.viewmodel.state.auth

import android.net.Uri
import ru.mangotest.core.UiText
import ru.mangotest.core.zodiac.zeroYearFormatter
import ru.mangotest.core.zodiac.zodiacSignResource
import ru.mangotest.data.remote.api.model.user.ProfileData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class UserScreenState(
    val user: ProfileData? = null,
    val isUpdating: Boolean = false,
    val isEditing: Boolean = false,
    val pickedPhotoUri: Uri? = null,

    val firstFetchError: UiText? = null
) {

    val userBirthdayAsLocalDate = user?.birthday?.let {
        LocalDate.parse(it)
    }

    val formattedBirthday = userBirthdayAsLocalDate?.format(
        DateTimeFormatter.ofPattern("dd.MM.yyyy")
    )

    val userZodiacSign: UiText? =
        user?.birthday?.let {
            UiText.StringResource(
                resId = LocalDate.parse(
                    it.replaceBefore('-', "0001"),
                    zeroYearFormatter
                ).zodiacSignResource
            )
        }
}