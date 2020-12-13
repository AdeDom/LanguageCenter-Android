package com.lc.android.presentation.splashscreen

import com.lc.android.base.BaseViewModel
import com.lc.library.sharedpreference.pref.PreferenceAuth

class SplashScreenViewModel(
    private val pref: PreferenceAuth,
) : BaseViewModel<SplashScreenViewState>(SplashScreenViewState) {

    fun isValidateSignIn(): Boolean {
        return pref.accessToken.isNotBlank()
    }

}
