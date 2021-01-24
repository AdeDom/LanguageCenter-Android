package com.lc.android.presentation.guide.nativelanguage

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.model.UserInfoLocaleItem
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import kotlinx.coroutines.launch

class GuideNativeLanguageViewModel(
    private val useCase: GetUserInfoUseCase,
) : BaseViewModel<GuideNativeLanguageViewState>(GuideNativeLanguageViewState()) {

    fun getSettingLocaleNatives() {
        launch {
            val localNatives = useCase.getDbUserInfo()?.localNatives
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

}
