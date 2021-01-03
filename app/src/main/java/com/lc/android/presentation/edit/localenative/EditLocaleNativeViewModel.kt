package com.lc.android.presentation.edit.localenative

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

class EditLocaleNativeViewModel(
    private val useCase: EditUserInfoUseCase,
    private val dataSource: LanguageCenterDataSource,
) : BaseViewModel<EditLocaleNativeViewState>(EditLocaleNativeViewState()) {

    private val _editLocaleNativeEvent = MutableLiveData<BaseResponse>()
    val editLocaleNativeEvent: LiveData<BaseResponse>
        get() = _editLocaleNativeEvent

    fun getLocaleNatives() {
        launch {
            val localNatives = dataSource.getDbUserInfo()?.localNatives
            val isCheckedTh = localNatives?.any { it.locale == "th" } ?: false
            val isCheckedEn = localNatives?.any { it.locale == "en" } ?: false

            val levelTh = localNatives?.singleOrNull { it.locale == "th" }?.level ?: 0
            val levelEn = localNatives?.singleOrNull { it.locale == "en" }?.level ?: 0

            val data = mutableListOf(
                UserInfoLocaleItem(locale = "th", level = levelTh, isChecked = isCheckedTh),
                UserInfoLocaleItem(locale = "en", level = levelEn, isChecked = isCheckedEn),
            )

            setState { copy(localNatives = data) }
        }
    }

    fun callEditLocale(localNatives: List<UserInfoLocaleItem>) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = EditLocaleRequest(
                editLocaleType = LanguageCenterConstant.LOCALE_NATIVE,
                locales = localNatives
                    .map {
                        UserInfoLocale(
                            locale = it.locale,
                            level = it.level,
                        )
                    }
            )
            when (val resource = useCase.callEditLocale(request)) {
                is Resource.Success -> _editLocaleNativeEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
