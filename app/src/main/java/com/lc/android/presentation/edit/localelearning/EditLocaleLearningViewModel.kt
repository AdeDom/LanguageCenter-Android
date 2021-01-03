package com.lc.android.presentation.edit.localelearning

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.usecase.EditUserInfoUseCase

class EditLocaleLearningViewModel(
    private val useCase: EditUserInfoUseCase,
) : BaseViewModel<EditLocaleLearningViewState>(EditLocaleLearningViewState())
