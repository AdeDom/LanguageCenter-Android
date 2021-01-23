package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.domain.repository.LanguageCenterRepository
import com.lc.library.presentation.usecase.FetchTalkUnreceivedUseCase
import com.lc.library.sharedpreference.pref.ConfigPref
import io.ktor.util.*
import kotlinx.coroutines.launch

@KtorExperimentalAPI
class MainViewModel(
    private val configPref: ConfigPref,
    private val repository: LanguageCenterRepository,
    private val useCase: FetchTalkUnreceivedUseCase,
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

    fun callFetchTalkUnreceived() {
        launch {
            when (val resource = useCase()) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

}
