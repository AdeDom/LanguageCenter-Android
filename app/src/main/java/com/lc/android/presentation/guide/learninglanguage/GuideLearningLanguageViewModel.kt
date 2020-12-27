package com.lc.android.presentation.guide.learninglanguage

import com.lc.android.base.BaseViewModel
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.model.UserInfoLocaleItem
import kotlinx.coroutines.launch

class GuideLearningLanguageViewModel(
    private val dataSource: LanguageCenterDataSource,
) : BaseViewModel<GuideLearningLanguageViewState>(GuideLearningLanguageViewState()) {

    fun getSettingLocaleLearnings() {
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

}
