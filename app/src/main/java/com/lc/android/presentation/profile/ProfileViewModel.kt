package com.lc.android.presentation.profile

import androidx.lifecycle.LiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.UserInfoEntity
import com.lc.library.presentation.usecase.GetUserInfoUseCase
import com.lc.library.presentation.usecase.SignOutUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val signOutUseCase: SignOutUseCase,
) : BaseViewModel<ProfileViewState>(ProfileViewState()) {

    val getDbUserInfoLiveData: LiveData<UserInfoEntity>
        get() = getUserInfoUseCase.getDbUserInfoLiveData()

    fun signOut() {
        launch {
            signOutUseCase()
        }
    }

}
