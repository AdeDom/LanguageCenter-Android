package com.lc.android.presentation.edit.localenative

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.usecase.EditUserInfoUseCase

class EditLocaleNativeViewModel(
    private val useCase: EditUserInfoUseCase,
) : BaseViewModel<EditLocaleNativeViewState>(EditLocaleNativeViewState())
