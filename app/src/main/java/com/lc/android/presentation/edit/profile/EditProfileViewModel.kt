package com.lc.android.presentation.edit.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.EditUserInfoUseCase
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import com.lc.server.models.request.EditProfileRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.util.LanguageCenterConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class EditProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val editUserInfoUseCase: EditUserInfoUseCase,
) : BaseViewModel<EditProfileViewState>(EditProfileViewState()) {

    private val channel = ConflatedBroadcastChannel<EditProfileGenderEvent>()

    private val _editProfileEvent = MutableLiveData<BaseResponse>()
    val editProfileEvent: LiveData<BaseResponse>
        get() = _editProfileEvent

    val getDbUserInfoLiveData: LiveData<UserInfoEntity>
        get() = getUserInfoUseCase.getDbUserInfoLiveData()

    fun process(action: EditProfileGenderEvent) {
        launch {
            channel.send(action)
        }
    }

    fun callEditProfile() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = EditProfileRequest(
                givenName = state.value?.givenName,
                familyName = state.value?.familyName,
                gender = state.value?.gender,
                birthDate = state.value?.birthDateLong,
                aboutMe = state.value?.aboutMe,
            )
            when (val resource = editUserInfoUseCase.callEditProfile(request)) {
                is Resource.Success -> _editProfileEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun setStateGivenName(givenName: String) {
        setState { copy(givenName = givenName) }
    }

    fun setStateFamilyName(familyName: String) {
        setState { copy(familyName = familyName) }
    }

    fun setStateAboutMe(aboutMe: String) {
        setState { copy(aboutMe = aboutMe) }
    }

    fun setStateBirthDate(birthDate: Long) {
        setState { copy(birthDateLong = birthDate) }
    }

    fun getDbUserInfo() {
        launch {
            val userInfo = getUserInfoUseCase.getDbUserInfo()

            // gender
            when (userInfo?.gender) {
                LanguageCenterConstant.GENDER_MALE -> {
                    setState {
                        copy(
                            genderEvent = EditProfileGenderEvent.Male,
                            gender = LanguageCenterConstant.GENDER_MALE,
                        )
                    }
                }
                LanguageCenterConstant.GENDER_FEMALE -> {
                    setState {
                        copy(
                            genderEvent = EditProfileGenderEvent.Female,
                            gender = LanguageCenterConstant.GENDER_FEMALE,
                        )
                    }
                }
            }

            // birth date
            userInfo?.birthDateLong?.let { setStateBirthDate(it) }
        }
    }

    init {
        channel.asFlow()
            .onEach { event ->
                when (event) {
                    is EditProfileGenderEvent.Male -> {
                        setState {
                            copy(
                                genderEvent = EditProfileGenderEvent.Male,
                                gender = LanguageCenterConstant.GENDER_MALE,
                            )
                        }
                    }
                    is EditProfileGenderEvent.Female -> {
                        setState {
                            copy(
                                genderEvent = EditProfileGenderEvent.Female,
                                gender = LanguageCenterConstant.GENDER_FEMALE,
                            )
                        }
                    }
                }
            }
            .catch { setError(it) }
            .launchIn(this)
    }

}
