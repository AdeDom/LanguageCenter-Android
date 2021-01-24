package com.lc.android.presentation.guide.learninglanguage

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.model.UserInfoLocaleItem
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import kotlinx.coroutines.launch

class GuideLearningLanguageViewModel(
    private val useCase: GetUserInfoUseCase,
) : BaseViewModel<GuideLearningLanguageViewState>(GuideLearningLanguageViewState()) {

    fun getSettingLocaleLearnings() {
        launch {
            val localLearnings = useCase.getDbUserInfo()?.localLearnings
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

}
