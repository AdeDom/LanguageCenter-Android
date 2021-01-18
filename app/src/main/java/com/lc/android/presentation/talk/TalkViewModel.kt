package com.lc.android.presentation.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.SendMessageUseCase
import com.lc.server.models.request.SendMessageRequest
import kotlinx.coroutines.launch

class TalkViewModel(
    private val useCase: SendMessageUseCase,
) : BaseViewModel<TalkViewState>(TalkViewState()) {

    private val _clearTextEvent = MutableLiveData<Unit>()
    val clearTextEvent: LiveData<Unit>
        get() = _clearTextEvent

    fun setStateMessages(messages: String) {
        setState {
            copy(
                messages = messages,
                isSendMessage = messages.isNotBlank(),
            )
        }
    }

    fun setStateToUserId(toUserId: String?) {
        setState { copy(toUserId = toUserId) }
    }

    fun callSendMessage() {
        launch {
            val message = state.value?.messages
            _clearTextEvent.value = Unit

            val request = SendMessageRequest(
                messages = message,
                toUserId = state.value?.toUserId,
            )
            when (val resource = useCase(request)) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

}
