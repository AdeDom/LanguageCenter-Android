package com.lc.android.presentation.guide.birthdate

import java.util.*

data class GuideBirthDateViewState(
    val birthDateCalendar: Calendar? = null,
    val birthDateString: String = "",
    val age: Int? = null,
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
