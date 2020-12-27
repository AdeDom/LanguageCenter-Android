package com.lc.android.presentation.guide.birthdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.guide.model.GuideUpdateProfileParcelable
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.GuideUpdateProfileUseCase
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.floor

class GuideBirthDateViewModel(
    private val useCase: GuideUpdateProfileUseCase,
) : BaseViewModel<GuideBirthDateViewState>(GuideBirthDateViewState()) {

    private val _guideUpdateProfileEvent = MutableLiveData<BaseResponse>()
    val guideUpdateProfileEvent: LiveData<BaseResponse>
        get() = _guideUpdateProfileEvent

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

    fun callGuideUpdateProfile(guideUpdateProfile: GuideUpdateProfileParcelable) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val localNatives = guideUpdateProfile.localNatives?.map {
                UserInfoLocale(locale = it.locale, level = it.level)
            }
            val localLearnings = guideUpdateProfile.localLearnings?.map {
                UserInfoLocale(locale = it.locale, level = it.level)
            }
            if (localNatives != null && localLearnings != null) {
                val request = GuideUpdateProfileRequest(
                    localNatives = localNatives,
                    localLearnings = localLearnings,
                    gender = guideUpdateProfile.gender,
                    birthDate = state.value?.birthDateCalendar?.timeInMillis,
                )
                when (val resource = useCase(request)) {
                    is Resource.Success -> _guideUpdateProfileEvent.value = resource.data
                    is Resource.Error -> setError(resource.throwable)
                }
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
