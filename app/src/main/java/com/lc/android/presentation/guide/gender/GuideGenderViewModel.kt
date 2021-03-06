package com.lc.android.presentation.guide.gender

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.usecase.GetUserInfoUseCase
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
class GuideGenderViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
) : BaseViewModel<GuideGenderViewState>(GuideGenderViewState()) {

    private val channel = ConflatedBroadcastChannel<GuideGenderEvent>()

    fun process(action: GuideGenderEvent) {
        launch {
            channel.send(action)
        }
    }

    fun getDbUserInfo() {
        launch {
            val userInfo = getUserInfoUseCase.getDbUserInfo()
            when (userInfo?.gender) {
                LanguageCenterConstant.GENDER_MALE -> {
                    setState {
                        copy(
                            genderEvent = GuideGenderEvent.Male,
                            gender = LanguageCenterConstant.GENDER_MALE,
                        )
                    }
                }
                LanguageCenterConstant.GENDER_FEMALE -> {
                    setState {
                        copy(
                            genderEvent = GuideGenderEvent.Female,
                            gender = LanguageCenterConstant.GENDER_FEMALE,
                        )
                    }
                }
            }
        }
    }

    init {
        channel.asFlow()
            .onEach { event ->
                when (event) {
                    is GuideGenderEvent.Male -> {
                        setState {
                            copy(
                                genderEvent = GuideGenderEvent.Male,
                                gender = LanguageCenterConstant.GENDER_MALE,
                            )
                        }
                    }
                    is GuideGenderEvent.Female -> {
                        setState {
                            copy(
                                genderEvent = GuideGenderEvent.Female,
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
