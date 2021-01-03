package com.lc.android.presentation.edit.localelearning

import com.lc.library.presentation.model.UserInfoLocaleItem

data class EditLocaleLearningViewState(
    val localLearnings: List<UserInfoLocaleItem> = emptyList(),
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
