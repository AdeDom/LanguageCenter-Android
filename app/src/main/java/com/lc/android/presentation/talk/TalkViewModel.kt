package com.lc.android.presentation.talk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.db.entities.TalkEntity
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.GetTalkUseCase
import com.lc.library.presentation.usecase.SendMessageUseCase
import com.lc.server.models.request.SendMessageRequest
import kotlinx.coroutines.launch

class TalkViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getTalkUseCase: GetTalkUseCase,
) : BaseViewModel<TalkViewState>(TalkViewState()) {

    private val _clearTextEvent = MutableLiveData<Unit>()
    val clearTextEvent: LiveData<Unit>
        get() = _clearTextEvent

    fun getDbTalkByOtherUserIdLiveData(otherUserId: String?): LiveData<List<TalkEntity>> {
        return getTalkUseCase.getDbTalkByOtherUserIdLiveData(otherUserId)
    }

    fun setStateMessages(messages: String) {
        setState {
            copy(
                messages = messages,
                isSendMessage = messages.isNotBlank(),
            )
        }
    }

    fun callSendMessage(toUserId: String?) {
        launch {
            val message = state.value?.messages
            _clearTextEvent.value = Unit

            val request = SendMessageRequest(
                messages = message,
                toUserId = toUserId,
            )
            when (val resource = sendMessageUseCase(request)) {
                is Resource.Error -> setError(resource.throwable)
            }
        }
    }

}
