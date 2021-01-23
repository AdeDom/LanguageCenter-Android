package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchTalkUnreceivedUseCase
import com.lc.library.presentation.usecase.TalkWebSocketsUseCase
import com.lc.library.sharedpreference.pref.ConfigPref
import io.ktor.util.*
import kotlinx.coroutines.launch

@KtorExperimentalAPI
class MainViewModel(
    private val configPref: ConfigPref,
    private val talkWebSocketsUseCase: TalkWebSocketsUseCase,
    private val fetchTalkUnreceivedUseCase: FetchTalkUnreceivedUseCase,
) : BaseViewModel<MainViewState>(MainViewState) {

    fun setSelectPage(selectPage: String) {
        configPref.selectPage = selectPage
    }

    fun getSelectPage() = configPref.selectPage

    fun incomingSendMessageSocket() {
        launch {
            talkWebSocketsUseCase()
            incomingSendMessageSocket()
        }
    }

    fun callFetchTalkUnreceived() {
        launch {
            when (val resource = fetchTalkUnreceivedUseCase()) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

}
