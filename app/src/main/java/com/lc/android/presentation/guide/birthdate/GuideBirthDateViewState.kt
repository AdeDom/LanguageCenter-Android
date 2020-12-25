package com.lc.android.presentation.guide.birthdate

import java.util.*

data class GuideBirthDateViewState(
    val birthDateCalendar: Calendar = Calendar.getInstance(),
    val birthDateString: String = "",
    val age: Int? = null,
)
