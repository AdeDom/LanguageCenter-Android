package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.FetchUserInfoUseCase
import com.lc.library.presentation.SignOutUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val fetchUserInfoUseCase: FetchUserInfoUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<MainViewState>(MainViewState()) {

    fun callFetchUserInfo() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchUserInfoUseCase()) {
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
        launch {
            signOutUseCase()
        }
    }

}
