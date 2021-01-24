package com.lc.android.presentation.splashscreen

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.usecase.FilePrefUseCase

class SplashScreenViewModel(
    private val pref: FilePrefUseCase,
) : BaseViewModel<SplashScreenViewState>(SplashScreenViewState) {

    fun isValidateSignIn(): Boolean {
        return pref.getAccessToken().isNotBlank()
    }

}
