package com.lc.android.presentation.friendgroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddChatGroupUseCase
import com.lc.library.presentation.usecase.FetchChatGroupUseCase
import com.lc.library.presentation.usecase.RemoveChatGroupUseCase
import com.lc.library.presentation.usecase.RenameChatGroupUseCase
import com.lc.server.models.model.ChatGroup
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.request.RenameChatGroupRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class FriendGroupViewModel(
    private val fetchChatGroupUseCase: FetchChatGroupUseCase,
    private val addChatGroupUseCase: AddChatGroupUseCase,
    private val renameChatGroupUseCase: RenameChatGroupUseCase,
    private val removeChatGroupUseCase: RemoveChatGroupUseCase,
) : BaseViewModel<FriendGroupViewState>(FriendGroupViewState()) {

    private val _chatGroupEvent = MutableLiveData<BaseResponse>()
    val chatGroupEvent: LiveData<BaseResponse>
        get() = _chatGroupEvent

    fun callFetchChatGroup() {
        launch {
            setState { copy(isLoading = true) }

            when (val resource = fetchChatGroupUseCase()) {
                is Resource.Success -> setState { copy(chatGroups = resource.data.chatGroups) }
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callAddChatGroup(groupName: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = AddChatGroupRequest(groupName = groupName)
            when (val resource = addChatGroupUseCase(request)) {
                is Resource.Success -> _chatGroupEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun callRenameChatGroup(groupName: String?) {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            val request = RenameChatGroupRequest(
                chatGroupId = state.value?.chatGroup?.chatGroupId,
                groupName = groupName,
            )
            when (val resource = renameChatGroupUseCase(request)) {
                is Resource.Success -> _chatGroupEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun callRemoveChatGroup() {
        launch {
            setState { copy(isLoading = true, isClickable = false) }

            when (val resource = removeChatGroupUseCase(state.value?.chatGroup?.chatGroupId)) {
                is Resource.Success -> _chatGroupEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

    fun setStateChatGroup(chatGroup: ChatGroup?) {
        setState { copy(chatGroup = chatGroup) }
    }

}
