package ru.mangotest.domain.use_case

import ru.mangotest.R
import ru.mangotest.core.UiText
import javax.inject.Inject

class NameValidationUseCase @Inject constructor() {
    operator fun invoke(name: String): UiText? =
        if (name.isBlank()) UiText.StringResource(R.string.name_empty)
        else null
}