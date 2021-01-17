package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: FetchFriendInfoUseCase,
) : BaseViewModel<MainViewState>(MainViewState) {

    fun callFetchFriendInfo() {
        launch {
            useCase.callFetchFriendInfo()
        }
    }

}
