package com.lc.android.presentation.profile

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.FetchUserInfoUseCase
import com.lc.library.presentation.SignOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val fetchUserInfoUseCase: FetchUserInfoUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<ProfileViewState>(ProfileViewState()) {

    fun callFetchUserInfo() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchUserInfoUseCase()) {
                is Resource.Success -> {
                    setState {
                        copy(
                            name = resource.data.userInfo?.name.orEmpty(),
                            picture = resource.data.userInfo?.picture,
                        )
                    }
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
