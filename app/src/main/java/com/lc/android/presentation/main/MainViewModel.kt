package com.lc.android.presentation.main

import com.lc.android.base.BaseViewModel
import com.lc.android.util.SingleLiveEvent
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchTalkUnreceivedUseCase
import com.lc.library.presentation.usecase.FilePrefUseCase
import com.lc.library.presentation.usecase.TalkWebSocketsUseCase
import io.ktor.util.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@KtorExperimentalAPI
class MainViewModel(
    private val filePrefUseCase: FilePrefUseCase,
    private val talkWebSocketsUseCase: TalkWebSocketsUseCase,
    private val fetchTalkUnreceivedUseCase: FetchTalkUnreceivedUseCase,
) : BaseViewModel<MainViewState>(MainViewState) {

    private var incomingJob: Job = Job()

    val talkWebSockets = SingleLiveEvent<Unit>()

    fun setSelectPage(selectPage: String) {
        filePrefUseCase.setSelectPage(selectPage)
    }

    fun getSelectPage() = filePrefUseCase.getSelectPage()

    fun incomingSendMessageSocket() {
        incomingJob.cancel()
        incomingJob = launch {
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

    fun talkWebSockets() = talkWebSockets.call()

    override fun onCleared() {
        incomingJob.cancel()
        super.onCleared()
    }

}
