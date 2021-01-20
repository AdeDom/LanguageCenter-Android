package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.sharedpreference.pref.ConfigPref
import io.ktor.util.*
import kotlinx.coroutines.launch

@KtorExperimentalAPI
class MainViewModel(
    private val configPref: ConfigPref,
    private val repository: LanguageCenterRepository,
) : BaseViewModel<MainViewState>(MainViewState) {

    fun setSelectPage(selectPage: String) {
        configPref.selectPage = selectPage
    }

    fun getSelectPage() = configPref.selectPage

    fun incomingSendMessageSocket() {
        launch {
            repository.incomingSendMessageSocket()
            incomingSendMessageSocket()
        }
    }

}
