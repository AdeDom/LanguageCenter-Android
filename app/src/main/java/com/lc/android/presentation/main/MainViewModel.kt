package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.sharedpreference.pref.ConfigPref

class MainViewModel(
    private val configPref: ConfigPref,
) : BaseViewModel<MainViewState>(MainViewState) {

    fun setSelectPage(selectPage: String) {
        configPref.selectPage = selectPage
    }

    fun getSelectPage() = configPref.selectPage

}
