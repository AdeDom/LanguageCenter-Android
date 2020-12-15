package com.lc.android.presentation.profile

import androidx.lifecycle.LiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.GetUserInfoUseCase
import com.lc.library.presentation.SignOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<ProfileViewState>(ProfileViewState()) {

    val getDbUserInfoLiveData: LiveData<UserInfoEntity>
        get() = getUserInfoUseCase.getDbUserInfoLiveData()

    fun callFetchUserInfo() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = getUserInfoUseCase.callFetchUserInfo()) {
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
