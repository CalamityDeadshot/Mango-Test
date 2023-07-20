package ru.mangotest.domain.use_case

import ru.mangotest.R
import ru.mangotest.core.UiText
import javax.inject.Inject

class UsernameValidationUseCase @Inject constructor() {

    private val validationRegex = "^[A-Za-z0-9_-]*\$".toRegex()

    operator fun invoke(username: String): UiText? {

        if (username.isEmpty()) return UiText.StringResource(R.string.username_empty)

        return if (validationRegex.matches(username)) null
        else UiText.StringResource(R.string.username_invalid)
    }
}