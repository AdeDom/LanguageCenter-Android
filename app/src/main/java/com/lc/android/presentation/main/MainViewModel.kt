package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.FetchUserInfoUseCase
import com.lc.library.sharedpreference.pref.PreferenceAuth
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: FetchUserInfoUseCase,
    private val pref: PreferenceAuth,
) : BaseViewModel<MainViewState>(MainViewState()) {

    fun callFetchUserInfo() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = useCase()) {
                is Resource.Success -> {
                    setState { copy(name = resource.data.userInfo?.name.orEmpty()) }
                }
                is Resource.Error -> {
                    setError(resource.throwable)
                }
            }

            setState { copy(isLoading = false) }
        }
    }

    fun signOut() {
        pref.accessToken = ""
        pref.refreshToken = ""
    }

}
