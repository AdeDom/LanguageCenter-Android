package com.lc.android.presentation.chatgroupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.ChatGroup
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.FetchChatGroupDetailUseCase
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class ChatGroupDetailViewModel(
    private val useCase: FetchChatGroupDetailUseCase,
) : BaseViewModel<ChatGroupDetailViewState>(ChatGroupDetailViewState()) {

    private val _chatGroupDetailEvent = MutableLiveData<BaseResponse>()
    val chatGroupDetailEvent: LiveData<BaseResponse>
        get() = _chatGroupDetailEvent

    fun callFetchChatGroupDetail() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            when (val resource = useCase(state.value?.chatGroupId)) {
                is Resource.Success -> setState { copy(chatGroupDetails = resource.data.chatGroupDetails) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun setStateChatGroups(chatGroupId: Int, chatGroups: List<ChatGroup>) {
        setState {
            copy(
                otherChatGroups = chatGroups.filter { it.chatGroupId != chatGroupId },
                chatGroupId = chatGroupId,
            )
        }
    }

    fun setStateFriendUserId(friendUserId: String?) {
        setState { copy(friendUserId = friendUserId) }
    }

    fun callChangeChatGroup(changeChatGroupId: Int?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val message =
                "${state.value?.chatGroupId}      ${state.value?.friendUserId}" +
                        "\n$changeChatGroupId"
            _chatGroupDetailEvent.value = BaseResponse(message = message)

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun callRemoveChatGroupDetail() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val message =
                "chatGroupId : ${state.value?.chatGroupId}\nfriendUserId : ${state.value?.friendUserId}"
            _chatGroupDetailEvent.value = BaseResponse(message = message)

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
