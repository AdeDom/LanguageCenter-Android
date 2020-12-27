package com.lc.android.presentation.guide.nativelanguage

import com.lc.android.base.BaseViewModel
import com.lc.library.data.network.source.LanguageCenterDataSource
import com.lc.library.presentation.model.UserInfoLocaleItem
import kotlinx.coroutines.launch

class GuideNativeLanguageViewModel(
    private val dataSource: LanguageCenterDataSource,
) : BaseViewModel<GuideNativeLanguageViewState>(GuideNativeLanguageViewState()) {

    fun getSettingLocaleNatives() {
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

}
