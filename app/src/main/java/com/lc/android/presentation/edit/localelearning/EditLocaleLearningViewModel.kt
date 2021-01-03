package com.lc.android.presentation.edit.localelearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.model.UserInfoLocaleItem
import com.lc.library.presentation.usecase.EditUserInfoUseCase
import com.lc.server.models.model.UserInfoLocale
import com.lc.server.models.request.EditLocaleRequest
import com.lc.server.models.response.BaseResponse
import com.lc.server.util.LanguageCenterConstant
import kotlinx.coroutines.launch

class EditLocaleLearningViewModel(
    private val useCase: EditUserInfoUseCase,
    private val dataSource: LanguageCenterDataSource,
) : BaseViewModel<EditLocaleLearningViewState>(EditLocaleLearningViewState()){

    private val _editLocaleLearningEvent = MutableLiveData<BaseResponse>()
    val editLocaleLearningEvent: LiveData<BaseResponse>
        get() = _editLocaleLearningEvent

    fun getLocaleLearnings() {
        launch {
            val localLearnings = dataSource.getDbUserInfo()?.localLearnings
            val isCheckedTh = localLearnings?.any { it.locale == "th" } ?: false
            val isCheckedEn = localLearnings?.any { it.locale == "en" } ?: false

            val levelTh = localLearnings?.singleOrNull { it.locale == "th" }?.level ?: 0
            val levelEn = localLearnings?.singleOrNull { it.locale == "en" }?.level ?: 0

            val data = mutableListOf(
                UserInfoLocaleItem(locale = "th", level = levelTh, isChecked = isCheckedTh),
                UserInfoLocaleItem(locale = "en", level = levelEn, isChecked = isCheckedEn),
            )

            setState { copy(localLearnings = data) }
        }
    }

    fun callEditLocale(localLearnings: List<UserInfoLocaleItem>) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = EditLocaleRequest(
                editLocaleType = LanguageCenterConstant.LOCALE_LEARNING,
                locales = localLearnings
                    .map {
                        UserInfoLocale(
                            locale = it.locale,
                            level = it.level,
                        )
                    }
            )
            when (val resource = useCase.callEditLocale(request)) {
                is Resource.Success -> _editLocaleLearningEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
