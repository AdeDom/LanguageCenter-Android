package com.lc.android.presentation.guideline.birthdate

import com.lc.android.base.BaseViewModel
import java.util.*
import kotlin.math.floor

class GuideBirthDateViewModel : BaseViewModel<GuideBirthDateViewState>(GuideBirthDateViewState()) {

    fun setStateBirthDate(birthDate: Calendar) {
        setState {
            copy(
                birthDateCalendar = birthDate,
                birthDateString = getStringBirthDate(birthDate),
                age = getAge(birthDate),
            )
        }
    }

    private fun getStringBirthDate(calendar: Calendar): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        return "$dayOfMonth/${month.plus(1)}/$year"
    }

    private fun getAge(calendar: Calendar): Int? {
        val now = System.currentTimeMillis()
        val birthDate = calendar.timeInMillis
        val timeBetween: Long = now - birthDate
        val yearsBetween: Double = timeBetween / 3.15576e+10
        val age = floor(yearsBetween).toInt()
        return if (age < 0) null else age
    }

}
