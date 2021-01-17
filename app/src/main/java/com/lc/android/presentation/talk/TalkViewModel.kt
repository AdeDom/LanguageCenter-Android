package com.lc.android.presentation.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TalkViewModel : BaseViewModel<TalkViewState>(TalkViewState()) {

    private val _clearTextEvent = MutableLiveData<Unit>()
    val clearTextEvent: LiveData<Unit>
        get() = _clearTextEvent

    private val _sendMessageEvent = MutableLiveData<BaseResponse>()
    val sendMessageEvent: LiveData<BaseResponse>
        get() = _sendMessageEvent

    fun setStateMessage(message: String) {
        setState { copy(message = message) }
    }

    fun callSendMessage() {
        launch {
            val message = state.value?.message
            _clearTextEvent.value = Unit
            setState { copy(isLoading = true) }

            delay(2_000)
            val messageResponse = "Send message {${message}} success"
            _sendMessageEvent.value = BaseResponse(success = true, message = messageResponse)

            setState { copy(isLoading = false) }
        }
    }

}
