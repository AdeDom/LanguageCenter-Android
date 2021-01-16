package com.lc.android.presentation.chatgroupdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.android.presentation.model.ChatGroup
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.ChangeChatGroupUseCase
import com.lc.library.presentation.usecase.FetchChatGroupDetailUseCase
import com.lc.library.presentation.usecase.RemoveChatGroupDetailUseCase
import com.lc.server.models.request.ChangeChatGroupRequest
import com.lc.server.models.request.RemoveChatGroupDetailRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class ChatGroupDetailViewModel(
    private val fetchChatGroupDetailUseCase: FetchChatGroupDetailUseCase,
    private val changeChatGroupUseCase: ChangeChatGroupUseCase,
    private val removeChatGroupDetailUseCase: RemoveChatGroupDetailUseCase,
) : BaseViewModel<ChatGroupDetailViewState>(ChatGroupDetailViewState()) {

    private val _chatGroupDetailEvent = MutableLiveData<BaseResponse>()
    val chatGroupDetailEvent: LiveData<BaseResponse>
        get() = _chatGroupDetailEvent

    fun callFetchChatGroupDetail() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            when (val resource = fetchChatGroupDetailUseCase(state.value?.chatGroupId)) {
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

            val request = ChangeChatGroupRequest(
                chatGroupId = state.value?.chatGroupId,
                friendUserId = state.value?.friendUserId,
                changeChatGroupId = changeChatGroupId,
            )
            when (val resource = changeChatGroupUseCase(request)) {
                is Resource.Success -> _chatGroupDetailEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun callRemoveChatGroupDetail() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = RemoveChatGroupDetailRequest(
                chatGroupId = state.value?.chatGroupId,
                friendUserId = state.value?.friendUserId,
            )
            when (val resource = removeChatGroupDetailUseCase(request)) {
                is Resource.Success -> _chatGroupDetailEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
