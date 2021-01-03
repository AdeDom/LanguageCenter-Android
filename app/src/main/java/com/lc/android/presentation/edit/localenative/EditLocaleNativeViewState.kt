package com.lc.android.presentation.edit.localenative

import com.lc.library.presentation.model.UserInfoLocaleItem

data class EditLocaleNativeViewState(
    val localNatives: List<UserInfoLocaleItem> = emptyList(),
    val isLoading: Boolean = false,
    val isClickable: Boolean = true,
)
