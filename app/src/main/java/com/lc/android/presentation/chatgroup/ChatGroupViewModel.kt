package com.lc.android.presentation.chatgroup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lc.android.base.BaseViewModel
import com.lc.library.data.repository.Resource
import com.lc.library.presentation.usecase.AddChatGroupUseCase
import com.lc.library.presentation.usecase.FetchChatGroupUseCase
import com.lc.server.models.request.AddChatGroupRequest
import com.lc.server.models.response.BaseResponse
import kotlinx.coroutines.launch

class ChatGroupViewModel(
    private val fetchChatGroupUseCase: FetchChatGroupUseCase,
    private val addChatGroupUseCase: AddChatGroupUseCase,
) : BaseViewModel<ChatGroupViewState>(ChatGroupViewState()) {

    private val _addChatGroupEvent = MutableLiveData<BaseResponse>()
    val addChatGroupEvent: LiveData<BaseResponse>
        get() = _addChatGroupEvent

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
                is Resource.Success -> _addChatGroupEvent.value = resource.data
                is Resource.Error -> setError(resource.throwable)
            }

            setState { copy(isLoading = false, isClickable = true) }
        }
    }

}
