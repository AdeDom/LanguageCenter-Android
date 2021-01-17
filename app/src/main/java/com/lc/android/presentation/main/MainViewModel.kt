package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.presentation.usecase.FetchFriendInfoUseCase
import com.lc.library.sharedpreference.pref.ConfigPref
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: FetchFriendInfoUseCase,
    private val configPref: ConfigPref,
) : BaseViewModel<MainViewState>(MainViewState) {

    fun callFetchFriendInfo() {
        launch {
            useCase.callFetchFriendInfo()
        }
    }

    fun setSelectPage(selectPage: String) {
        configPref.selectPage = selectPage
    }

    fun getSelectPage() = configPref.selectPage

}
