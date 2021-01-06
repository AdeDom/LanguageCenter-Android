package com.lc.android.presentation.guide.birthdate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.GuideUpdateProfileParcelable
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import com.lc.library.presentation.usecase.GuideUpdateProfileUseCase
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.GuideUpdateProfileRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class GuideBirthDateViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val useCase: GuideUpdateProfileUseCase,
) : BaseViewModel<GuideBirthDateViewState>(GuideBirthDateViewState()) {

    private val _guideUpdateProfileEvent = MutableLiveData<BaseResponse>()
    val guideUpdateProfileEvent: LiveData<BaseResponse>
        get() = _guideUpdateProfileEvent

    fun setStateBirthDate(birthDate: Long) {
        setState { copy(birthDateLong = birthDate) }
    }

    fun getAge(time: Long?): Int? {
        return time?.let { calendar ->
            val now = System.currentTimeMillis()
            val timeBetween: Long = now - calendar
            val yearsBetween: Double = timeBetween / 3.15576e+10
            val age = kotlin.math.floor(yearsBetween).toInt()
            if (age < 0) null else age
        }
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
                    birthDate = state.value?.birthDateLong,
                )
                when (val resource = useCase(request)) {
                    is Resource.Success -> _guideUpdateProfileEvent.value = resource.data
                    is Resource.Error -> setError(resource.throwable)
                }
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun getDbUserInfo() {
        launch {
            getUserInfoUseCase.getDbUserInfo()?.birthDateLong?.let { birthDateLong ->
                setStateBirthDate(birthDateLong)
            }
        }
    }

}
